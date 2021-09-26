package com.hyc.report.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.hyc.report.entity.*;
import com.hyc.report.exception.ReportException;
import com.hyc.report.response.ResultCode;
import com.hyc.report.service.DBChangeService;
import com.hyc.report.service.ReportDatabaseService;
import com.hyc.report.service.ReportService;
import com.hyc.report.response.Result;
import com.hyc.report.util.PageInfoUtil;
import com.hyc.report.util.SqlUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

@Api(tags = "清单报表查询管理")
@Slf4j
@RequestMapping("/qdReport")
@RestController
public class ReportQdController {

    //切换数据库使用的service接口
    @Resource
    private DBChangeService dbChangeServiceImpl;

    @Resource
    private ReportService reportService;

    @Resource
    private ReportDatabaseService reportDatabaseService;

    /**
     * 点击详情按钮查询运营定义字段和报表清单主要信息
     * */
    @ApiOperation(value = "点击详情返回业务信息",notes = "击详情按钮查询运营定义字段和报表清单主要信息"
            , httpMethod = "GET"
            , produces = "application/json"
            , protocols = "http"
    )
    @GetMapping("getReportBusiness")
    public Result getReportBusiness(@ApiParam(value = "报表ID",required = true)@RequestParam("reportId") int reportId
            ,@ApiParam(value = "报表详情ID",required = true)@RequestParam("reportDetailId") int reportDetailId) throws Exception {
        //应该按照report_detail_id和reportId查询
        /*int reportDetailId = 102;
        int reportId = 142;*/
        List<Business> qdBusinessById = reportService.getQdBusinessById(reportDetailId);

        List<String> businessList = new ArrayList<>();
        for (int i = 0; i < qdBusinessById.size(); i++) {
            businessList.add(qdBusinessById.get(i).getBusinessField().toUpperCase());
        }

        List<Field> fieldList = reportService.getQdFieldById(reportDetailId);
        String sql = "";
        for (int i = 0; i < fieldList.size(); i++) {
            if(fieldList.get(i).getSqlType().equals("主SQL")){
                sql = fieldList.get(i).getSqlContent();
                break;
            }
        }
        List<String> tableList = SqlUtil.getTableNameBySql(sql);

        /**
         * 思路：
         * 1.首先要查表结构肯定要切换到相应数据库
         * 2.多张表查询正确字段，首先表名要一个个查，另外查询SQL语句要用 in(未知字段，正确字段....)，
         * 然后通过循环将一个个查好的表结构字段都放入到自己定义的list中，语句需要做双循环
         * */
        ReportCondition reportDetailInfo = reportService.getReportDetailInfo(reportId);
        dbChangeServiceImpl.changeDb(reportDetailInfo.getDatabaseName());
        List<BusinessInfo> businessInfos = new ArrayList<>();
        for (int i = 0 ; i<tableList.size();i++) {
            List<BusinessInfo> businessInfoList = reportService.getBusinessInfoList(businessList,tableList.get(i).toUpperCase());
            for (int j = 0 ;j<businessInfoList.size();j++) {
                businessInfos.add(businessInfoList.get(j));
            }
        }

        log.info("表结构输出:{}",businessInfos);
        return Result.ok().data("businessInfos",businessInfos);
    }

    /**
     * 通过报表配置查询数据
     * 思路：
     * 1.首先获取前端业务人员配置的条件信息
     * 2.再次通过条件查询出配置人员的查询脚本
     * 3.把脚本跟条件信息进行判断配置连接
     * 4.查询该报表对应数据库名称并切换数据库
     * 5.将组好的SQL放mybatis跑,然后分页输出
     * 注意：要看如果只有主SQL但是却有业务条件的话，sql语句不能赋值and
     * */
    @ApiOperation(value = "通过报表查询信息",notes = "点击查询获取报表信息，通过前端发送的当前页、每页长度参数和报表查询条件参数获取数据库报表信息"
            , httpMethod = "POST"
            , produces = "application/json"
            , protocols = "http"
    )
    @PostMapping("/getReportData")
    public Result getReportData(@ApiParam(value = "报表详情查询信息",required = true)@RequestBody ReportDetail reportDetail
            ,@ApiParam(value = "当前页",required = true)@RequestParam("current") int current
            ,@ApiParam(value = "查询长度",required = true)@RequestParam("size") int size) throws Exception {
        /*int reportDetailId = 102;
        int reportId = 142;
        ReportDetail reportDetail =new ReportDetail();
        reportDetail.setReportId(reportId);
        reportDetail.setReportDetailId(reportDetailId);
        BusinessInfo business1 = new BusinessInfo();
        BusinessInfo business2 = new BusinessInfo();
        business1.setColumnName("username");
        business1.setDataValue("HH");
        business1.setDataType("VARCHAR2");
        business2.setColumnName("password");
        business2.setDataType("VARCHAR2");
        *//*List<String> stringList = new ArrayList<>();
        stringList.add("2021-09-22 00:00:00");
        stringList.add("2021-09-24 00:00:00");*//*
        business2.setDataValue("123");
        List<BusinessInfo> businessList = new ArrayList<>();
        businessList.add(business1);
        businessList.add(business2);
        reportDetail.setBusinessInfoList(businessList);*/

        List<Field> fieldList = reportService.getQdFieldById(reportDetail.getReportDetailId());

        String sql = "",mainSql = "",cSql = "",condition = "",Sql="";
        if (fieldList.size()==1) {
            sql=fieldList.get(0).getSqlContent();
            if (reportDetail.getBusinessInfoList().size()>0) {
                sql+=" where ";
                for (int i = 0; i < reportDetail.getBusinessInfoList().size(); i++) {
                    condition =SqlUtil.sqlCompose(reportDetail.getBusinessInfoList().get(i).getDataType(), reportDetail.getBusinessInfoList().get(i).getColumnName(),reportDetail.getBusinessInfoList().get(i).getDataValue());
                    sql=(i!=reportDetail.getBusinessInfoList().size()-1)?(sql+= condition+" and "):(sql+=condition);
                }
            }
        }else {
            for (int i = 0 ; i< fieldList.size(); i++) {
                if (fieldList.get(i).getSqlType().equals("主SQL")) {
                    mainSql+=fieldList.get(i).getSqlContent()+" ";
                }else if (fieldList.get(i).getSqlType().equals("从SQL")) {
                    cSql+=fieldList.get(i).getSqlContent()+" ";
                }
            }
            sql=mainSql+cSql;
            for (int i = 0 ; i< reportDetail.getBusinessInfoList().size(); i++) {
                condition ="and "+SqlUtil.sqlCompose(reportDetail.getBusinessInfoList().get(i).getDataType(),
                        reportDetail.getBusinessInfoList().get(i).getColumnName()
                        ,reportDetail.getBusinessInfoList().get(i).getDataValue())+" ";
                sql+=condition;
            }
        }

        log.info("组建的SQL:"+sql);

        //切换到数据库dbtest2
        dbChangeServiceImpl.changeDb(reportService.getReportDetailInfo(reportDetail.getReportId()).getDatabaseName());
        List<LinkedHashMap<String, Object>> reportData = reportService.getReportData(sql);
        PageInfo pageInfo = PageInfoUtil.getPageInfoBylist(reportData, size, current);
        return Result.ok()
                .data("list",pageInfo.getList())
                .data("total",pageInfo.getTotal());
    }

    /**
     * 插入报表
     * */
    @ApiOperation(value = "插入报表",notes = "通过报表信息和数据库名称插入数据"
            , httpMethod = "POST"
            , produces = "application/json"
            , protocols = "http"
    )
    @PostMapping("/insertQdReport")
    @Transactional(rollbackFor = Exception.class)
    public Result insertQdReport(@ApiParam(value = "插入配置报表信息",required = true)@RequestBody ReportDetail reportDetail
            ,@ApiParam(value = "配置报表数据库名称",required = true)@RequestParam("databaseName") String databaseName) {
        /**
         * 测试数据
         * */
        /*ReportDetail reportDetail = new ReportDetail();
        reportDetail.setReportName("测试2");
        reportDetail.setReportDescribe("啦啦啦啦啦");

        List<Business> businessList = new ArrayList<>();
        businessList.add(new Business(null,"id"));
        businessList.add(new Business(null,"password"));
        reportDetail.setBusinessField(businessList);

        List<Field> fieldList = new ArrayList<>();
        fieldList.add(new Field(null,"主SQL","select * from testHycDEPT"));
        fieldList.add(new Field(null,"从SQL","where id = '1'"));
        fieldList.add(new Field(null,"从SQL","and username = 'hyc'"));
        reportDetail.setFieldList(fieldList);
        reportDetail.setDatabaseName("ZJCSC517");*/

//        String databaseName = "ZJCSC517";

        Integer reportId = reportService.selectReportIdByName(reportDetail.getReportName());
        if (reportId!=null) {
            log.error("该报表已存在，请重新修改");
            throw new ReportException(ResultCode.REPORT_INSERT_REPEAT.getCode()
                    ,ResultCode.REPORT_INSERT_REPEAT.getMessage());
        }
        Integer databaseId =reportDatabaseService.getDataBaseIdByName(databaseName);
        reportDetail.setDatabaseId(databaseId);
        log.info("测试输出：{}",reportDetail.getDatabaseId());
        try{
            reportService.insertReportMain(reportDetail);
            reportId = reportService.selectReportIdByName(reportDetail.getReportName());
            reportDetail.setReportId(reportId);
            reportService.insertReportDetail(reportDetail);
            Integer reportDetailId = reportService.getReportDetailId(reportDetail.getReportName());
            reportDetail.setReportDetailId(reportDetailId);
            reportService.insertReportDetailCondition(reportDetail);
            return Result.ok().data(ResultCode.REPORT_INSERT_SUCCESS.getCode()
                    ,ResultCode.REPORT_INSERT_SUCCESS.getMessage());
        }catch (Exception e) {
            log.error("e:{}",e.getMessage());
            throw new ReportException(ResultCode.REPORT_INSERT_ERROR.getCode()
                    ,ResultCode.REPORT_INSERT_ERROR.getMessage());
        }
    }

    /**
     * 查询清单报表
     * */
    @ApiOperation(value = "报表页面查询",notes = "报表查询，还是通过当前页面、页面长度和报表名称进行模糊查询分页输出"
            , httpMethod = "GET"
            , produces = "application/json"
            , protocols = "http"
    )
    @GetMapping("/getQdReport")
    public Result getQdReport(@ApiParam(value = "查询当前页",required = true) @RequestParam("current") int current
            ,@ApiParam(value = "查询条数",required = true) @RequestParam("size") int size
            ,@ApiParam(value = "查询报表名称",required = false)@RequestParam("reportName") String reportName) {
        /*int current = 1;
        int size = 5;
        String reportName = null;*/
        try{
            log.info("*****正在执行查询报表查询接口");
            Page<ReportDetail> pageInfo = reportService.getReportInfo(current, size, reportName);
            log.info("*****执行结束，结果已输出,报表查询数据为第{}页",current);
            return Result.ok().data("total",pageInfo.getTotal()).data("records",pageInfo.getResult());
        }catch (Exception e){
            log.error("*****查询清单报表失败");
            e.printStackTrace();
            throw new ReportException(ResultCode.REPORT_QUERY_ERROR.getCode(),ResultCode.REPORT_QUERY_ERROR.getMessage());
        }
    }

    /**
     * 删除报表
     * */
    @ApiOperation(value = "报表删除",notes = "通过获取的的id集合执行删除"
            , httpMethod = "POST"
            , produces = "application/json"
            , protocols = "http"
    )
    @PostMapping("deleteReportByIds/{Ids}")
    @Transactional(rollbackFor = Exception.class)
    public Result deleteReportByIds(@Param("报表的reportId集合")@PathVariable List<Integer> Ids) {
        /*List<Integer> Ids = new ArrayList<>();
        Ids.add(129);
        Ids.add(130);*/
//        Ids.add(39);
        if (Ids.size()>0) {
            try{
                List<Integer> delIds = reportService.getDelId(Ids);
                reportService.deleteReportByIds(Ids,delIds);
                return Result.ok().data(ResultCode.REPORT_DELETE_SUCCESS.getCode(),ResultCode.REPORT_DELETE_SUCCESS.getMessage());
            }catch (Exception e){
                e.printStackTrace();
//                throw new ReportException(ResultCode.REPORT_DELETE_ERROR.getCode(),ResultCode.REPORT_DELETE_ERROR.getMessage());
                return Result.error();
            }
        }
        return Result.error();
    }

    /**
     * 通过清单报表查询点击编辑获取自定义字段名称及类型（待定）
     * */
    @ApiOperation(value = "点击编辑页面后获取当前页面信息查询",notes = "点击编辑页面后获取当前页面信息查询"
            , httpMethod = "GET"
            , produces = "application/json"
            , protocols = "http"
    )
    @GetMapping("getQdDetailByName")
    public Result getQdDetailByName(@ApiParam("报表名称")@RequestParam("reportName") String reportName) {
//        String reportName = "测试2";
        try {
            ReportDetail queryInfo = reportService.getQdDetailByName(reportName);
            log.info("查询数据为：{}",queryInfo);
            LambdaQueryWrapper<ReportDatabase> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(ReportDatabase::getDatabaseId,queryInfo.getDatabaseId());
            String databaseName = reportDatabaseService.getOne(lambdaQueryWrapper).getDatabaseName();
            log.info("查询数据库配置为:{}",databaseName);
            List<Business> businessList=reportService.getQdBusinessById(queryInfo.getReportDetailId());
            List<Field> fieldList=reportService.getQdFieldById(queryInfo.getReportDetailId());
            return Result.ok().data("queryInfo",queryInfo).data("databaseName",databaseName)
                    .data("businessList",businessList)
                    .data("fieldList",fieldList);
        }catch (Exception e){
            log.error(e.getMessage());
            log.error("查询报表失败");
            return Result.error().data("message","查询报表失败");
        }
    }

    /**
     * 更新报表清单数据（待定）
     * */
    @ApiOperation(value = "更新报表配置",notes = "更新报表配置"
            , httpMethod = "POST"
            , produces = "application/json"
            , protocols = "http"
    )
    @PostMapping("updateReportDataConfig")
    @Transactional(rollbackFor = Exception.class)
    public Result updateReportDataConfig(@ApiParam("更新报表信息") @RequestBody ReportDetail reportDetail
            ,@RequestParam("databaseName") String databaseName) {
        //假设这个是接收到的对象
        /*ReportDetail reportDetail = new ReportDetail();

        //这些都是查询时候就固定好的id
        reportDetail.setReportDetailId(101);
        //这些都是查询时候就固定好的id
        reportDetail.setReportId(141);

        reportDetail.setReportName("生产29090");
        reportDetail.setReportDescribe("这是修改啦~~~");

        List<Business> businessList = new ArrayList<>();
        businessList.add(new Business(null,"username"));
        reportDetail.setBusinessField(businessList);

        List<Field> fieldList = new ArrayList<>();
        fieldList.add(new Field(null,"主SQL","select * from testHycDEPT"));
        fieldList.add(new Field(null,"从SQL","where password = '345'"));
        reportDetail.setFieldList(fieldList);*/

//        String databaseName = "更新数据库哦";
        Integer databaseId = reportDatabaseService.getDataBaseIdByName(databaseName);
        reportDetail.setDatabaseId(Math.toIntExact(databaseId));
        try {
            reportService.updateReportDataConfig(reportDetail);
            return Result.ok().data(ResultCode.REPORT_UPDATE_SUCCESS.getCode(),ResultCode.REPORT_UPDATE_SUCCESS.getMessage());
        }catch (Exception e){
            log.error("报表配置修改失败");
            throw new ReportException(ResultCode.REPORT_UPDATE_ERROR.getCode(),ResultCode.REPORT_UPDATE_ERROR.getMessage());
        }
    }

}
