package com.hyc.report.mapper;

import com.hyc.report.entity.ReportDatabase;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

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


    int updateDatabase(@Param("reportDatabase") ReportDatabase reportDatabase);
}
