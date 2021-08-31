package com.hyc.report.entity;

import lombok.Data;

@Data
public class ReportCondition {
    private String databaseName;
    private String fieldList;
    private String businessList;
    private String reportName;
}
