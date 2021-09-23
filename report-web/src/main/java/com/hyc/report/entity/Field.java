package com.hyc.report.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("SQL脚本存储")
@Data
public class Field {
    @ApiModelProperty(value = "主ID，自增",example = "23")
    private Integer ID;
    @ApiModelProperty(value = "清单报表详情ID",example = "123")
    private Integer reportDetailId;
    @ApiModelProperty(value = "SQL脚本类型",example = "主SQL/从SQL（就两种）")
    private String sqlType;
    @ApiModelProperty(value = "SQL脚本内容",example = "主SQL:select * from testHycDEPT")
    private String sqlContent;

    public Field(Integer reportDetailId, String sqlType, String sqlContent) {
        this.reportDetailId = reportDetailId;
        this.sqlType = sqlType;
        this.sqlContent = sqlContent;
    }

    public Field() {
    }
}
