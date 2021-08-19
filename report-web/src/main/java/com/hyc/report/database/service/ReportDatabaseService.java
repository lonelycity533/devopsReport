package com.hyc.report.database.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.Page;
import com.hyc.report.database.entity.ReportDatabase;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hyc
 * @since 2021-08-16
 */
public interface ReportDatabaseService extends IService<ReportDatabase> {

    Page<ReportDatabase> getDataBaseByName(int current, int size, String databaseName);

    int insertDatabase(ReportDatabase reportDatabase);

    int updateDatabase(ReportDatabase reportDatabase);
}
