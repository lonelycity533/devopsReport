package com.hyc.report.entity;

import lombok.Data;

import java.util.List;

@Data
public class ReportCondition {
    private String databaseName;
    private String fieldList;
    private String businessField;
    private String reportName;
}
