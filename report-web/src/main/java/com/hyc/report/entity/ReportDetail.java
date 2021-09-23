package com.hyc.report.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@ApiModel("清单报表详情")
@Data
//@Getter
public class ReportDetail extends ReportMain{
    @ApiModelProperty(value = "清单报表详情ID",example = "1")
    private Integer reportDetailId;
    @ApiModelProperty(value = "查询SQL配置集合",example = "1")
    private List<Field> fieldList;
    @ApiModelProperty(value = "查询返回业务字段集合",example = "1")
    private List<Business> businessField;
    @ApiModelProperty(value = "查询返回业务字段集合（包含类型和值）",example = "1")
    private List<BusinessInfo> businessInfoList;
    @ApiModelProperty(value = "数据库ID",example = "63")
    private int databaseId;

}
