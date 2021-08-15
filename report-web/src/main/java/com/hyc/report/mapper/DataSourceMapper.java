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
//@Mapper
public interface DataSourceMapper {

    /**
     * String datasourceId;
     *     String url;
     *     String userName;
     *     String passWord;
     *     String code;
     *     String databasetype;
     * */
    @Results({
            @Result(column = "user_name",property = "userName"),
            @Result(column = "url",property = "url"),
            @Result(column = "pass_word",property = "passWord"),
            @Result(column = "databasetype",property = "databasetype"),
            @Result(column = "code",property = "code"),
            @Result(column = "datasource_id",property = "datasourceId")

    })
    @Select("select * from databasesource")
    List<DataSource> get();

}
