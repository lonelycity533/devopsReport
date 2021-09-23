package com.hyc.report.controller;

import com.alibaba.fastjson.JSON;
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
import com.hyc.report.util.StringConvertList;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("getReportBusiness")
    public Result getReportBusiness() throws Exception {
        //应该按照report_detail_id查询
        int reportDetailId = 102;
        int reportId = 142;
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
     * */
//    @ApiOperation(value = "清单报表查询",notes = "根据清单报表配置好的条件查询报表数据")
    @GetMapping("/getReportData")
    public Result getReportData() throws Exception {
        ReportDetail reportDetail =new ReportDetail();
        reportDetail.setReportId(40);
        ReportCondition reportDetailInfo = reportService.getReportDetailInfo(reportDetail.getReportId());
        log.info("获取到的报表数据库信息以及前端传送的一些信息：{}",reportDetailInfo.toString());

        /*List<Map<String, Object>> maps = StringConvertList.toListMap(reportDetailInfo.getFieldList());
        log.info("获取的json数据转换为list:{}",maps);
        String sql = "";
        for (int i = 0 ; i<maps.size(); i++) {
            sql+=maps.get(i).get("field_value")+" ";
        }
        log.info("拼接好的SQL语句为：{}",sql);*/

        //切换到数据库dbtest2
        dbChangeServiceImpl.changeDb(reportDetailInfo.getDatabaseName());
//        String sql = "select * from testHycDEPT";
//        List<LinkedHashMap<String, Object>> reportData = reportService.getReportData(sql);
////        List results = userService.selectLatestData(deviceAdd);
//        int pageSize = 5;
//        Integer pageIndex = 2;
//        PageInfo pageInfo = PageInfoUtil.getPageInfoBylist(reportData, 5, 1);
//        DBContextHolder.clearDataSource();
        /*return Result.ok()
                .data("list",pageInfo.getList())
                .data("total",pageInfo.getTotal());*/
        return null;
    }

    /**
     * 插入报表
     * */
    @PostMapping("/insertQdReport")
    @Transactional(rollbackFor = Exception.class)
    public Result insertQdReport() {
        /**
         * 测试数据
         * */
        ReportDetail reportDetail = new ReportDetail();
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

        String databaseName = "ZJCSC517";

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
    @GetMapping("/getQdReport")
    public Result getQdReport() {
        int current = 1;
        int size = 5;
        String reportName = null;
        try{
            log.info("*****正在执行查询报表查询接口");
            Page<ReportDetail> pageInfo = reportService.getReportInfo(current, size, reportName);
            log.info("*****执行结束，结果已输出,报表查询数据为第{}页",current);
            return Result.ok().data("total",pageInfo.getTotal()).data("records",pageInfo.getResult());
        }catch (Exception e){
            log.error("*****查询清单报表失败");
            throw new ReportException(ResultCode.REPORT_QUERY_ERROR.getCode(),ResultCode.REPORT_QUERY_ERROR.getMessage());
        }
    }

    /**
     * 删除报表
     * */
    @PostMapping("deleteReportByIds")
    @Transactional(rollbackFor = Exception.class)
    public Result deleteReportByIds() {
        List<Integer> Ids = new ArrayList<>();
        Ids.add(129);
        Ids.add(130);
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
    @GetMapping("getQdDetailByName")
    public Result getQdDetailByName() {
        String reportName = "测试2";
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
            log.error("查询报表失败");
            return Result.error().data("message","查询报表失败");
        }
    }

    /**
     * 编辑报表清单数据（待定）
     * */
    @PostMapping("updateReportDataConfig")
    @Transactional(rollbackFor = Exception.class)
    public Result updateReportDataConfig() {
        //假设这个是接收到的对象
        ReportDetail reportDetail = new ReportDetail();

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
        reportDetail.setFieldList(fieldList);

        String databaseName = "更新数据库哦";
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
