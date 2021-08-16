package com.hyc.report.report.controller;

import com.hyc.report.report.service.DBChangeService;
import com.hyc.report.response.Result;
import com.hyc.report.report.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hyc.report.dynamiconfig.entity.DataSource;
import java.util.LinkedHashMap;
import java.util.List;

@Slf4j
@RequestMapping("/report")
@RestController
public class ReportController {

    @Autowired
    private DBChangeService dbChangeServiceImpl;


    @Autowired
    private ReportService reportService;

    @GetMapping("/getReportList")
    public Result getReportList() throws Exception {
        List<DataSource> dataSources = dbChangeServiceImpl.get();
        log.info("数据库数据源数据：{}",dataSources);
        //切换到数据库dbtest2
        String datasourceId="网厅测试";
        dbChangeServiceImpl.changeDb(datasourceId);
        String sql = "select * from testHycDEPT";
        List<LinkedHashMap<String, Object>> reportData = reportService.getReportData(sql);
//        DBContextHolder.clearDataSource();
        return Result.ok().data("success",reportData);
    }
}
