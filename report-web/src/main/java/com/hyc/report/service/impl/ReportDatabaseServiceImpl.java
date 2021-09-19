package com.hyc.report.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hyc.report.entity.ReportDatabase;
import com.hyc.report.exception.ReportException;
import com.hyc.report.mapper.ReportDatabaseMapper;
import com.hyc.report.response.ResultCode;
import com.hyc.report.service.ReportDatabaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

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
        try{
            Page<ReportDatabase> page = PageHelper.startPage(current, size);
            reportDatabaseMapper.getDataBaseByName(databaseName);
            return page;
        }catch (Exception e){
            log.error(ResultCode.REPORT_DATABASE_ERROR.getMessage());
            throw new ReportException(ResultCode.REPORT_DATABASE_ERROR.getCode(),ResultCode.REPORT_DATABASE_ERROR.getMessage());
        }
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
    public Integer getDataBaseIdByName(String databaseName) {
        try{
            LambdaQueryWrapper<ReportDatabase> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(ReportDatabase::getDatabaseName,databaseName);
            return reportDatabaseMapper.selectOne(lambdaQueryWrapper).getDatabaseId();
        }catch (Exception e){
            log.error("该数据库配置不存在");
            throw new ReportException(ResultCode.REPORT_QUERY_DATABASE.getCode(),
                    ResultCode.REPORT_QUERY_DATABASE.getMessage());
        }
    }

    @Override
    public List<String> getDatabaseTypeList() {
        return reportDatabaseMapper.getDatabaseTypeList();
    }
}
