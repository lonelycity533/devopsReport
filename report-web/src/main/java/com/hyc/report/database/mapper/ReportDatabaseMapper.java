package com.hyc.report.database.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hyc.report.database.entity.ReportDatabase;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hyc
 * @since 2021-08-16
 */
public interface ReportDatabaseMapper extends BaseMapper<ReportDatabase> {

    List<ReportDatabase> getDataBaseByName(@Param("databaseName") String databaseName);

    int insertDatabase(@Param("reportDatabase") ReportDatabase reportDatabase);
}
