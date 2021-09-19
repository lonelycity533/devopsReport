package com.hyc.report.entity;

import lombok.Data;

@Data
public class Business {
    private Integer ID;
    private Integer reportDetailId;
    private String businessField;

    public Business(Integer reportDetailId, String businessField) {
        this.reportDetailId = reportDetailId;
        this.businessField = businessField;
    }

    public Business() {
    }
}
