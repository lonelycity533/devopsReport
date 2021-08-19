package com.hyc.report.database.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.Page;
import com.hyc.report.database.entity.ReportDatabase;
import com.hyc.report.database.service.ReportDatabaseService;
import com.hyc.report.exception.ReportException;
import com.hyc.report.response.Result;
import com.hyc.report.response.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
    public Result getDatabaseList(@RequestParam(value = "current",required = true,defaultValue = "1") int current,
                                  @RequestParam(value = "size",required = true,defaultValue = "5") int size,
                                  @RequestParam("databaseName") String databaseName) {
//        String databaseName = "h";
        log.info("*****正在执行查询数据库配置接口");
        Page<ReportDatabase> pageInfo = reportDatabaseService.getDataBaseByName(current, size, databaseName);
        log.info("*****执行结束，结果已输出,数据库配置数据为第{}页",current);
        log.info("*****当前页数据为：{}",pageInfo.getResult());
        return Result.ok().data("total",pageInfo.getTotal()).data("records",pageInfo.getResult());
    }

    /**
     * 插入数据库数据
     * */
    @PostMapping("/insertDatabase")
    public Result insertDatabase(@RequestBody ReportDatabase reportDatabase) {
        /*ReportDatabase reportDatabase1 = new ReportDatabase();
        reportDatabase1.setDatabaseName("短厅");
        reportDatabase1.setDatabasePassword("123");
        reportDatabase1.setDatabaseType("mysql");
        reportDatabase1.setDatabaseUsername("sdf");
        reportDatabase1.setDatabaseUrl("sdfsdf");*/
        log.info("*****正在执行插入数据库配置接口");
        LambdaQueryWrapper<ReportDatabase>  lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ReportDatabase::getDatabaseName,reportDatabase.getDatabaseName());
        int count = reportDatabaseService.count(lambdaQueryWrapper);
        if (count>0) {
            log.info("*****数据库配置添加重复");
            throw new ReportException(ResultCode.DATABASE_INSERT_REPEAT.getCode(),ResultCode.DATABASE_INSERT_REPEAT.getMessage());
        }
        int saveCount = 0;
        try{
            saveCount = reportDatabaseService.insertDatabase(reportDatabase);
        }catch (Exception e) {
            log.info("*****数据库配置添加失败");
            throw new ReportException(ResultCode.DATABASE_INSERT_ERROR.getCode(),ResultCode.DATABASE_INSERT_ERROR.getMessage());
        }
        if (saveCount>0) {
            log.info("*****数据库配置添加成功");
            return Result.ok().data(ResultCode.DATABASE_INSERT_SUCCESS.getCode(),ResultCode.DATABASE_INSERT_SUCCESS.getMessage());
        } else {
            log.info("*****数据库配置无添加");
            return Result.ok().data("400","无添加数据");
        }
    }

    @PostMapping("/updateDatabase")
    public Result updateDatabase(/*@RequestBody ReportDatabase reportDatabase*/) {
        ReportDatabase reportDatabase1 = new ReportDatabase();
        reportDatabase1.setDatabaseId((long) 63);
        reportDatabase1.setDatabaseName("短厅wangting");
        reportDatabase1.setDatabasePassword("12345");
        reportDatabase1.setDatabaseType("mysql");
        reportDatabase1.setDatabaseUsername("sdf");
        reportDatabase1.setDatabaseUrl("sdfsdf");

        log.info("*****正在执行更新数据库配置接口");
        int updateCount;
//        try{
        updateCount = reportDatabaseService.updateDatabase(reportDatabase1);
//        }catch (Exception e) {
//            log.info("*****数据库配置更新失败");
//            throw new ReportException(ResultCode.DATABASE_UPDATE_ERROR.getCode(),ResultCode.DATABASE_UPDATE_ERROR.getMessage());
//        }
        if (updateCount>0) {
            log.info("*****数据库配置更新成功");
            return Result.ok().data(ResultCode.DATABASE_UPDATE_SUCCESS.getCode(),ResultCode.DATABASE_UPDATE_SUCCESS.getMessage());
        } else {
            log.info("*****数据库配置无更新");
            return Result.ok().data("400","无更新数据");
        }
    }

    /*@PostMapping("/updateDatabase")
    public Result deleteDatabase(*//*@RequestBody ReportDatabase reportDatabase*//*) {
        ReportDatabase reportDatabase1 = new ReportDatabase();
        reportDatabase1.setDatabaseName("短厅");
        reportDatabase1.setDatabasePassword("123");
        reportDatabase1.setDatabaseType("mysql");
        reportDatabase1.setDatabaseUsername("sdf");
        reportDatabase1.setDatabaseUrl("sdfsdf");
        log.info("*****正在执行更新数据库配置接口");
        boolean updateFlag;
        try{
            LambdaQueryWrapper<ReportDatabase> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(ReportDatabase::getDatabaseId,reportDatabase1.getDatabaseId());
            updateFlag = reportDatabaseService.update(reportDatabase1,lambdaQueryWrapper);
        }catch (Exception e) {
            log.info("*****数据库配置更新失败");
            throw new ReportException(ResultCode.DATABASE_UPDATE_ERROR.getCode(),ResultCode.DATABASE_UPDATE_ERROR.getMessage());
        }
        if (updateFlag) {
            log.info("*****数据库配置更新成功");
            return Result.ok().data(ResultCode.DATABASE_UPDATE_SUCCESS.getCode(),ResultCode.DATABASE_UPDATE_SUCCESS.getMessage());
        } else {
            log.info("*****数据库配置无更新");
            return Result.ok().data("400","无更新数据");
        }
    }*/
}

