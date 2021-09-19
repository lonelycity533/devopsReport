package com.hyc.report.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.hyc.report.entity.ReportCondition;
import com.hyc.report.entity.ReportDatabase;
import com.hyc.report.entity.ReportDetail;
import com.hyc.report.exception.ReportException;
import com.hyc.report.response.ResultCode;
import com.hyc.report.service.DBChangeService;
import com.hyc.report.service.ReportDatabaseService;
import com.hyc.report.service.ReportService;
import com.hyc.report.response.Result;
import com.hyc.report.util.PageInfoUtil;
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

    @Resource
    private DBChangeService dbChangeServiceImpl;

    @Resource
    private ReportService reportService;

    @Resource
    private ReportDatabaseService reportDatabaseService;

//    @ApiOperation(value = "清单报表查询",notes = "根据清单报表配置好的条件查询报表数据")
    @GetMapping("/getReportData")
    public Result getReportData() throws Exception {
        ReportDetail reportDetail =new ReportDetail();
        reportDetail.setReportId(40);
        ReportCondition reportDetailInfo = reportService.getReportDetailInfo(reportDetail.getReportId());
        log.info("获取到的报表数据库信息以及前端传送的一些信息：{}",reportDetailInfo.toString());
        List<Map<String, Object>> maps = StringConvertList.toListMap(reportDetailInfo.getFieldList());
        log.info("获取的json数据转换为list:{}",maps);
        String sql = "";
        for (int i = 0 ; i<maps.size(); i++) {
            sql+=maps.get(i).get("field_value")+" ";
        }
        log.info("拼接好的SQL语句为：{}",sql);

        //切换到数据库dbtest2
        dbChangeServiceImpl.changeDb(reportDetailInfo.getDatabaseName());
//        String sql = "select * from testHycDEPT";
        List<LinkedHashMap<String, Object>> reportData = reportService.getReportData(sql);
//        List results = userService.selectLatestData(deviceAdd);
        int pageSize = 5;
        Integer pageIndex = 2;
        PageInfo pageInfo = PageInfoUtil.getPageInfoBylist(reportData, 5, 1);
//        DBContextHolder.clearDataSource();
        return Result.ok()
                .data("list",pageInfo.getList())
                .data("total",pageInfo.getTotal());
    }

    @PostMapping("/insertQdReport")
    @Transactional(rollbackFor = Exception.class)
    public Result insertQdReport() {
        /**
         * 测试数据
         * */
        ReportDetail reportDetail = new ReportDetail();
        reportDetail.setReportName("ceshi111df");
        reportDetail.setReportDescribe("啦啦啦啦啦");
        List<String> businessList = new ArrayList<>();
        businessList.add("username");
        businessList.add("password");
        reportDetail.setBusinessField(businessList.toString());

        List<Map<String,Object>> fieldList = new ArrayList<>();
        Map<String, Object> map3= new HashMap<>();
        map3.put("field_name","主SQL");
        map3.put("field_value","select * from testHYCDEP");
        Map<String, Object> map4= new HashMap<>();
        map4.put("field_name","从SQL");
        map4.put("field_value","where id = 1");
        fieldList.add(map3);
        fieldList.add(map4);
        reportDetail.setFieldList(fieldList.toString());

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
            return Result.ok().data(ResultCode.REPORT_INSERT_SUCCESS.getCode()
                    ,ResultCode.REPORT_INSERT_SUCCESS.getMessage());
        }catch (Exception e) {
            log.error("e:{}",e.getMessage());
            throw new ReportException(ResultCode.REPORT_INSERT_ERROR.getCode()
                    ,ResultCode.REPORT_INSERT_ERROR.getMessage());
        }
    }

    @GetMapping("/getQdReport")
    public Result getQdReport() {
        int current = 1;
        int size = 5;
        String reportName = "测试";
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

    @GetMapping("getQdDetailByName")
    public Result getQdDetailByName() {
        String reportName = "测试1";
        try {
            ReportDetail queryInfo = reportService.getQdDetailByName(reportName);
            log.info("查询数据为：{}",queryInfo);
            LambdaQueryWrapper<ReportDatabase> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(ReportDatabase::getDatabaseId,queryInfo.getDatabaseId());
            String databaseName = reportDatabaseService.getOne(lambdaQueryWrapper).getDatabaseName();
            log.info("查询数据库配置为:{}",databaseName);
            return Result.ok().data("queryInfo",queryInfo).data("databaseName",databaseName);
        }catch (Exception e){
            log.error("查询报表失败");
            return Result.error().data("message","查询报表失败");
        }
    }

    @PostMapping("updateReportDataConfig")
    @Transactional(rollbackFor = Exception.class)
    public Result updateReportDataConfig() {
        //假设这个是接收到的对象
        ReportDetail reportDetail = new ReportDetail();

        //这些都是查询时候就固定好的id
        reportDetail.setReportDetailId(8);
        //这些都是查询时候就固定好的id
        reportDetail.setReportId(24);

        reportDetail.setReportName("生产29090");
        reportDetail.setReportDescribe("这是修改啦~~~");

        List<Map<String,Object>> businessList = new ArrayList<>();
        Map<String, Object> map1= new HashMap<>();
        map1.put("field_name","username");
        map1.put("field_type","varchar2");
        Map<String, Object> map2= new HashMap<>();
        map2.put("field_name","password");
        map2.put("field_type","number");
        businessList.add(map1);
        businessList.add(map2);
        reportDetail.setBusinessField(businessList.toString());

        List<Map<String,Object>> fieldList = new ArrayList<>();
        Map<String, Object> map3= new HashMap<>();
        map3.put("field_name","主SQL");
        map3.put("field_value","select * from testHYCDEPT");
        Map<String, Object> map4= new HashMap<>();
        map4.put("field_name","从SQL");
        map4.put("field_value","where id = 1");
        fieldList.add(map3);
        fieldList.add(map4);
        reportDetail.setFieldList(fieldList.toString());

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

    @PostMapping("deleteReportByIds")
    @Transactional(rollbackFor = Exception.class)
    public Result deleteReportByIds() {
        List<Integer> Ids = new ArrayList<>();
//        Ids.add(37);
//        Ids.add(38);
//        Ids.add(39);
        if (Ids.size()>0) {
            try{
                reportService.deleteReportByIds(Ids);
            }catch (Exception e){
                throw new ReportException(ResultCode.REPORT_DELETE_ERROR.getCode(),ResultCode.REPORT_DELETE_ERROR.getMessage());
            }
            return Result.ok().data(ResultCode.REPORT_DELETE_SUCCESS.getCode(),ResultCode.REPORT_DELETE_SUCCESS.getMessage());
        }
        return Result.error();
    }
}
