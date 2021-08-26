package com.hyc.report.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hyc.report.entity.ReportDatabase;
import com.hyc.report.mapper.ReportDatabaseMapper;
import com.hyc.report.service.ReportDatabaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hyc
 * @since 2021-08-16
 */
@Service("ReportDatabaseService")
public class ReportDatabaseServiceImpl extends ServiceImpl<ReportDatabaseMapper, ReportDatabase> implements ReportDatabaseService {

    @Resource
    private ReportDatabaseMapper reportDatabaseMapper;


    @Override
    public Page<ReportDatabase> getDataBaseByName(int current, int size, String databaseName) {
        Page<ReportDatabase> page = PageHelper.startPage(current, size);
        reportDatabaseMapper.getDataBaseByName(databaseName);
        return page;
    }

    @Override
    public int insertDatabase(ReportDatabase reportDatabase) {
        return reportDatabaseMapper.insertDatabase(reportDatabase);
    }

    @Override
    public int updateDatabase(ReportDatabase reportDatabase) {
        return reportDatabaseMapper.updateDatabase(reportDatabase);
    }

    @Override
    public Long getDataBaseIdByName(String databaseName) {
        LambdaQueryWrapper<ReportDatabase> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ReportDatabase::getDatabaseName,databaseName);
        return reportDatabaseMapper.selectOne(lambdaQueryWrapper).getDatabaseId();
    }
}
