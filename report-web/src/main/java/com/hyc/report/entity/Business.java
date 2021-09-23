package com.hyc.report.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("业务传递字段")
@Data
public class Business {

    @ApiModelProperty(value = "主ID",example = "1")
    private Integer ID;
    @ApiModelProperty(value = "清单报表详情ID",example = "137")
    private Integer reportDetailId;
    @ApiModelProperty(value = "清单报表详情ID",example = "137")
    private String businessField;

    public Business(Integer reportDetailId, String businessField) {
        this.reportDetailId = reportDetailId;
        this.businessField = businessField;
    }

    public Business() {
    }
}
