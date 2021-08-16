package com.hyc.report.mapper;

import com.hyc.report.dynamiconfig.entity.DataSource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author : hyc
 * @CreateTime : 2019/10/23
 * @Description :
 **/
public interface DataSourceMapper {

    /**
     * private int databaseId;
     *     private String databaseName;
     *     private String databaseUrl;
     *     private String databaseUsername;
     *     private String databasePassword;
     *     private String databaseType;
     * */
    @Results({
        @Result(column = "database_name",property = "databaseName"),
        @Result(column = "database_url",property = "databaseUrl"),
        @Result(column = "database_username",property = "databaseUsername"),
        @Result(column = "database_password",property = "databasePassword"),
        @Result(column = "database_type",property = "databaseType"),
    })
    @Select("select * from REPORT_DATABASE")
    List<DataSource> get();

}
