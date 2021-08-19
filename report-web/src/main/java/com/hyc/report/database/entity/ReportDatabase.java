package com.hyc.report.database.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hyc.report.BaseEntity;
import com.sun.javafx.beans.IDProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author hyc
 * @since 2021-08-16
 */
@Data
@KeySequence(value = "DATABASE_NEWSID")
@TableName("REPORT_DATABASE")
public class ReportDatabase /*extends BaseEntity*/ implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableId(value="DATABASE_ID",type = IdType.INPUT)
    private Long databaseId;

    @TableField("DATABASE_NAME")
    private String databaseName;

    @TableField("DATABASE_URL")
    private String databaseUrl;

    @TableField("DATABASE_USERNAME")
    private String databaseUsername;

    @TableField("DATABASE_PASSWORD")
    private String databasePassword;

    @TableField("DATABASE_TYPE")
    private String databaseType;

    @ApiModelProperty(value = "系统创建时间",example = "2020-12-28 03:25:37")
    @TableField(fill = FieldFill.INSERT_UPDATE)//创建注解
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "系统更新时间",example = "2020-12-28 03:25:37")
    @TableField(fill = FieldFill.INSERT_UPDATE)//更新注解
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
