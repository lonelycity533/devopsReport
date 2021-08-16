package com.hyc.report.controller;

import com.hyc.report.dynamiconfig.context.DBContextHolder;
import com.hyc.report.service.DBChangeService;
import com.hyc.report.response.Result;
import com.hyc.report.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hyc.report.dynamiconfig.entity.DataSource;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
public class TestController {

    @Autowired
    private DBChangeService dbChangeServiceImpl;


    @Autowired
    private ReportService reportService;

    @GetMapping("report/test")
    public Result get() throws Exception {
        List<DataSource> dataSources = dbChangeServiceImpl.get();
        //切换到数据库dbtest2
        String datasourceId="loginsecurity";
        dbChangeServiceImpl.changeDb(datasourceId);
        String sql = "select * from t_user";
        List<LinkedHashMap<String, Object>> reportData = reportService.getReportData(sql);
//        DBContextHolder.clearDataSource();
        return Result.ok().data("success",reportData);
    }
}
