package com.hyc.report.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("主清单报表")
@Data
public class ReportMain extends BaseEntity{
    @ApiModelProperty(value = "清单报表主ID",example = "129")
    private int reportId;
    @ApiModelProperty(value = "清单报表主ID",example = "测试2")
    private String reportName;
    @ApiModelProperty(value = "清单报表主ID",example = "啦啦啦啦啦这是修改拉")
    private String reportDescribe;
}
