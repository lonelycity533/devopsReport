package com.hyc.report.database.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
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
@EqualsAndHashCode(callSuper = false)
@TableName("REPORT_DATABASE")
@ApiModel(value="ReportDatabase对象", description="报表配置数据源")
public class ReportDatabase implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("DATABASE_ID")
    private BigDecimal databaseId;

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


}
