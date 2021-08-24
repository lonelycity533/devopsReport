package com.hyc.report.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.Page;
import com.hyc.report.entity.ReportDatabase;
import com.hyc.report.service.ReportDatabaseService;
import com.hyc.report.exception.ReportException;
import com.hyc.report.response.Result;
import com.hyc.report.response.ResultCode;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
@Api(tags="数据源配置管理")
@RestController
@RequestMapping("/database")
public class ReportDatabaseController {

    @Resource
    private ReportDatabaseService reportDatabaseService;

    /**
     * 分页查询数据源配置数据
     * */
    @ApiOperation(value = "数据源配置数据遍历",notes = "通过页数、长度和模糊查询数据源配置名称条件进行遍历"
            , httpMethod = "GET"
            , produces = "application/json"
            , protocols = "http"
    )
//    @ApiResponses(
//            {
//                    @ApiResponse(code = 20000, message = "成功", response = Result.class),
//                    @ApiResponse(code = 50000, message = "失败", response = Result.class),
//            }
//    )
    @GetMapping("/getDatabaseList")
    public Result getDatabaseList(@ApiParam(value = "当前页",required = true)@RequestParam(value = "current",required = true,defaultValue = "1") int current,
                                  @ApiParam(value = "每页数量",required = true)@RequestParam(value = "size",required = true,defaultValue = "5") int size,
                                  @ApiParam(value = "数据源配置名称",required = true)@RequestParam("databaseName") String databaseName) {
        /*int current = 1;
        int size = 5;
        String databaseName = "";*/
        log.info("*****正在执行查询数据库配置接口");
        Page<ReportDatabase> pageInfo = reportDatabaseService.getDataBaseByName(current, size, databaseName);
        log.info("*****执行结束，结果已输出,数据库配置数据为第{}页",current);
        log.info("*****当前页数据为：{}",pageInfo.getResult());
        return Result.ok().data("total",pageInfo.getTotal()).data("records",pageInfo.getResult());
    }

    /**
     * 添加数据源配置数据
     * */
    @ApiOperation(value = "数据源配置数据添加"
            ,notes = "通过数据源添加对象进行添加"
            , httpMethod = "POST"
            , produces = "application/json"
            , protocols = "http"
    )
    @ApiResponses(
            {
                    @ApiResponse(code = 20000, message = "成功", response = ReportDatabase.class),
            }
    )
    @PostMapping("/insertDatabase")
    public Result insertDatabase(@ApiParam(value = "数据源添加对象",required = true) @RequestBody ReportDatabase reportDatabase) {
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

    @ApiImplicitParams(@ApiImplicitParam())
    @ApiOperation(value = "数据源配置数据修改",notes = "通过数据源添加对象进行修改", httpMethod = "POST"
            , produces = "application/json"
            , protocols = "http")
    @PostMapping("/updateDatabase")
    public Result updateDatabase(@ApiParam(value = "数据源修改对象",required = true)@RequestBody ReportDatabase reportDatabase) {
        /*ReportDatabase reportDatabase1 = new ReportDatabase();
        reportDatabase1.setDatabaseId((long) 63);
        reportDatabase1.setDatabaseName("短厅wangting");
        reportDatabase1.setDatabasePassword("12345");
        reportDatabase1.setDatabaseType("mysql");
        reportDatabase1.setDatabaseUsername("sdf");
        reportDatabase1.setDatabaseUrl("sdfsdf");*/

        log.info("*****正在执行更新数据库配置接口");
        int updateCount;
        try{
            updateCount = reportDatabaseService.updateDatabase(reportDatabase);
        }catch (Exception e) {
            log.info("*****数据库配置更新失败");
            throw new ReportException(ResultCode.DATABASE_UPDATE_ERROR.getCode(),ResultCode.DATABASE_UPDATE_ERROR.getMessage());
        }
        if (updateCount>0) {
            log.info("*****数据库配置更新成功");
            return Result.ok().data(ResultCode.DATABASE_UPDATE_SUCCESS.getCode(),ResultCode.DATABASE_UPDATE_SUCCESS.getMessage());
        } else {
            log.info("*****数据库配置无更新");
            return Result.ok().data("400","无更新数据");
        }
    }

    @ApiOperation(value = "数据源配置数据单个删除或多个删除",notes = "通过数据源id集合进行单个或多个删除", httpMethod = "POST"
            , produces = "application/json"
            , protocols = "http")
    @PostMapping("/removeDatabase/{ids}")
    public Result removeDatabase(@ApiParam(value = "删除数据源id集合",required = true)@PathVariable List<Integer> ids) {
       /* List<Integer> ids = new ArrayList<>();
        ids.add(67);*/
//        ids.add(20);
//        ids.add(61);
        log.info("*****正在执行删除数据库配置接口");
        boolean delFlag;
        try{
            delFlag = reportDatabaseService.removeByIds(ids);
        }catch (Exception e) {
            log.info("*****数据库配置删除失败");
            throw new ReportException(ResultCode.DATABASE_DELETE_ERROR.getCode(),ResultCode.DATABASE_DELETE_ERROR.getMessage());
        }
        if (delFlag) {
            log.info("*****数据库配置删除成功");
            return Result.ok().data(ResultCode.DATABASE_DELETE_SUCCESS.getCode(),ResultCode.DATABASE_DELETE_SUCCESS.getMessage());
        } else {
            log.info("*****数据库配置无法删除");
            return Result.ok().data("400","无删除数据");
        }
    }
}
