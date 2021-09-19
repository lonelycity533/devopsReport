package com.hyc.report.entity;

import lombok.Data;

@Data
public class Field {
    private Integer ID;
    private Integer reportDetailId;
    private String sqlType;
    private String sqlContent;

    public Field(Integer reportDetailId, String sqlType, String sqlContent) {
        this.reportDetailId = reportDetailId;
        this.sqlType = sqlType;
        this.sqlContent = sqlContent;
    }

    public Field() {
    }
}
