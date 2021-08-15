package com.hyc.report.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportException extends RuntimeException {
    private Integer code;

    private String errMsg;
}
