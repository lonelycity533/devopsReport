package com.hyc.report.database.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hyc.report.database.entity.ReportDatabase;
import com.hyc.report.database.service.ReportDatabaseService;
import com.hyc.report.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hyc
 * @since 2021-08-16
 */
@Slf4j
@RestController
@RequestMapping("/report/database")
public class ReportDatabaseController {

    @Resource
    private ReportDatabaseService reportDatabaseService;

    @GetMapping("/getDatabaseList")
    public Result getDatabaseList(@RequestParam(required = true,defaultValue = "1") int current,
                                  @RequestParam(required = true,defaultValue = "5") int size) {
        Page<ReportDatabase> page = new Page<>(1,5);
        //lamba注入条件
        LambdaQueryWrapper<ReportDatabase> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ReportDatabase::getDatabaseName,"asdf");
        Page<ReportDatabase> userPage = reportDatabaseService.page(page,queryWrapper);
        long total = userPage.getTotal();
        //records是当前页查出来的数据
        List<ReportDatabase> records = userPage.getRecords();
        return Result.ok().data("total",total).data("records",records);
    }

}

