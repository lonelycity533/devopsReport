package com.hyc.report.controller;

import com.hyc.report.dynamiconfig.entity.DataSource;
import com.hyc.report.service.DBChangeService;
import com.hyc.report.service.ReportService;
import com.hyc.report.response.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;

@Api("清单报表查询管理")
@RestController
public class ReportController {

    @Resource
    private DBChangeService dbChangeServiceImpl;

    @Resource
    private ReportService reportService;

    @ApiOperation(value = "清单报表查询",notes = "根据清单报表配置好的条件查询报表数据")
    @GetMapping("/report/test")
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
}
