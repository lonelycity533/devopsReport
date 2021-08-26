package com.hyc.report.service;

import com.github.pagehelper.Page;
import com.hyc.report.entity.ReportDatabase;
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

    Long getDataBaseIdByName(String databaseName);
}
