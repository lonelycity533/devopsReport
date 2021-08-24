package com.hyc.report.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author hyc
 * @since 2021-08-16
 */
@Api("数据源配置类")
@Data
@TableName("REPORT_DATABASE")
public class ReportDatabase extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "数据源配置id",example = "1")
    @TableId(value="DATABASE_ID",type = IdType.INPUT)
    private Long databaseId;

    @ApiModelProperty(value = "数据源配置名称",example = "B2I数据源")
    @TableField("DATABASE_NAME")
    private String databaseName;

    @ApiModelProperty(value = "数据源配置地址",example = "jdbc:oracle:thin:@134.96.162.186:1521:wttest1")
    @TableField("DATABASE_URL")
    private String databaseUrl;

    @ApiModelProperty(value = "数据源配置用户名",example = "wttest1")
    @TableField("DATABASE_USERNAME")
    private String databaseUsername;

    @ApiModelProperty(value = "数据源配置密码",example = "99gY_52")
    @TableField("DATABASE_PASSWORD")
    private String databasePassword;

    @ApiModelProperty(value = "数据源配置类型",example = "oracle")
    @TableField("DATABASE_TYPE")
    private String databaseType;


}
