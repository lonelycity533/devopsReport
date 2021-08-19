package com.hyc.report.report.controller;

import com.hyc.report.dynamiconfig.entity.DataSource;
import com.hyc.report.report.service.DBChangeService;
import com.hyc.report.report.service.ReportService;
import com.hyc.report.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
public class TestController {

    @Resource
    private DBChangeService dbChangeServiceImpl;


    @Resource
    private ReportService reportService;

    @GetMapping("/report/test")
    public Result get() throws Exception {
        List<DataSource> dataSources = dbChangeServiceImpl.get();
        //切换到数据库dbtest2
        String datasourceId="网厅测试";
        dbChangeServiceImpl.changeDb(datasourceId);
        String sql = "select * from testHycDEPT";
        List<LinkedHashMap<String, Object>> reportData = reportService.getReportData(sql);
//        DBContextHolder.clearDataSource();
        return Result.ok().data("success",reportData);
    }
}
