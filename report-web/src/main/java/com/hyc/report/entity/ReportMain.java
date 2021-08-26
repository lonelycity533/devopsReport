package com.hyc.report.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class ReportMain extends BaseEntity{
    private int reportId;
    private String reportName;
    private String reportDescribe;
}
