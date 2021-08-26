package com.hyc.report.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.Page;
import com.hyc.report.dynamiconfig.entity.DataSource;
import com.hyc.report.entity.ReportDatabase;
import com.hyc.report.entity.ReportDetail;
import com.hyc.report.entity.ReportMain;
import com.hyc.report.exception.ReportException;
import com.hyc.report.response.ResultCode;
import com.hyc.report.service.DBChangeService;
import com.hyc.report.service.ReportDatabaseService;
import com.hyc.report.service.ReportService;
import com.hyc.report.response.Result;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.Wrapper;
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
    @GetMapping("/test")
    public Result get() throws Exception {

        /*List results = userService.selectLatestData(deviceAdd);
        int pageSize = 10;
        Integer pageIndex = deviceAdd.getPageIndex();
        Page page = new Page(pageIndex, pageSize);
        int total = results.size();
        page.setTotal(total);
        int startIndex = (pageIndex - 1) * pageSize;
        int endIndex = Math.min((startIndex + pageSize), total);
        page.addAll(results.subList(startIndex, endIndex));
        PageInfo pageInfo = new PageInfo(page);
        return new ResponseVo<>(ResponseType.RegisterSuccess.getStatus(),"success", pageInfo);*/

        List<DataSource> dataSources = dbChangeServiceImpl.get();
        //切换到数据库dbtest2
        String datasourceId="网厅测试";
        dbChangeServiceImpl.changeDb(datasourceId);
        String sql = "select * from testHycDEPT";
        List<LinkedHashMap<String, Object>> reportData = reportService.getReportData(sql);
//        DBContextHolder.clearDataSource();
        return Result.ok().data("success",reportData);
    }

    @PostMapping("/insertQdReport")
    public Result insertQdReport() {
        /**
         * 测试数据
         * */
        ReportDetail reportDetail = new ReportDetail();
        reportDetail.setReportName("生产2");
        reportDetail.setReportDescribe("啦啦啦啦啦");

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
        String databaseName = "短厅wangting";

        int insertMainFlag = reportService.insertReportMain(reportDetail);
        if (insertMainFlag>0) {
            try{
                int reportId = reportService.selectReportIdByName(reportDetail.getReportName());
                log.info("测试输出报表Id：{}",reportId);
                reportDetail.setReportId(reportId);
                Long databaseId =reportDatabaseService.getDataBaseIdByName(databaseName);
                if (!(databaseId>0)) {
                    log.error("该数据库配置不存在");
                    throw new ReportException(ResultCode.REPORT_QUERY_DATABASE.getCode(),
                            ResultCode.REPORT_QUERY_DATABASE.getMessage());
                }
                reportDetail.setDatabaseId(Math.toIntExact(databaseId));
                log.info("测试输出：{}",reportDetail.getDatabaseId());
                int insertDetailFlag = reportService.insertReportDetail(reportDetail);
                if (!(insertDetailFlag>0)) {
                    log.info("报表插入异常，请检查代码");
                    throw new ReportException(ResultCode.REPORT_INSERT_ERROR.getCode()
                            ,ResultCode.REPORT_INSERT_ERROR.getMessage());
                }
            }catch (Exception e) {
                log.error("该报表已存在，请重新修改");
                throw new ReportException(ResultCode.REPORT_INSERT_REPEAT.getCode()
                        ,ResultCode.REPORT_INSERT_REPEAT.getMessage());
            }
        }else {
            log.error("报表插入异常，请检查代码");
            return Result.ok()
                    .data(ResultCode.REPORT_INSERT_ERROR.getCode()
                    ,ResultCode.REPORT_INSERT_ERROR.getMessage());
        }
        return Result.ok()
                .data(ResultCode.REPORT_INSERT_SUCCESS.getCode()
                        ,ResultCode.REPORT_INSERT_SUCCESS.getMessage());
    }

    @GetMapping("/getQdReport")
    public Result getQdReport() {
        int current = 1;
        int size = 5;
        String reportName = "测试";
        log.info("*****正在执行查询报表查询接口");
        Page<ReportDetail> pageInfo = reportService.getReportInfo(current, size, reportName);
        log.info("*****执行结束，结果已输出,报表查询数据为第{}页",current);
        return Result.ok().data("total",pageInfo.getTotal()).data("records",pageInfo.getResult());
    }

    @GetMapping("getQdDetailByName")
    public Result getQdDetailByName() {
        String reportName = "测试1";
        ReportDetail queryInfo = reportService.getQdDetailByName(reportName);
        log.info("查询数据为：{}",queryInfo);
        LambdaQueryWrapper<ReportDatabase> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ReportDatabase::getDatabaseId,queryInfo.getDatabaseId());
        String databaseName = reportDatabaseService.getOne(lambdaQueryWrapper).getDatabaseName();
        log.info("查询数据库配置为:{}",databaseName);
        return Result.ok().data("queryInfo",queryInfo).data("databaseName",databaseName);
    }

    @PostMapping("updateReportDataConfig")
    public Result updateReportDataConfig() {
        ReportDetail reportDetail = new ReportDetail();
        reportDetail.setReportName("生产29090");
        reportDetail.setReportDescribe("这是修改啦~~~");

        reportDetail.setReportDetailId(8);

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

        reportDetail.setReportId(24);

        int i = reportService.updateReportDataConfig(reportDetail);
        return null;
    }
}
