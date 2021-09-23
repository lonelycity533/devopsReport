package com.hyc.report.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("业务字段结构")
@Data
public class BusinessInfo {
    @ApiModelProperty(value = "字段名称",example = "username")
    private String columnName;
    @ApiModelProperty(value = "字段样式",example = "varchar2")
    private String dataType;
    @ApiModelProperty(value = "字段值",example = "hyc")
    private Object dataValue;
}
