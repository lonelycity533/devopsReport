package com.hyc.report;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Api("报表系统公共类")
@Data
public abstract class BaseEntity {
    @ApiModelProperty(value = "系统创建时间",example = "2020-12-28 03:25:37")
    @TableField(fill = FieldFill.INSERT_UPDATE)//创建注解
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "系统更新时间",example = "2020-12-28 03:25:37")
    @TableField(fill = FieldFill.INSERT_UPDATE)//更新注解
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
