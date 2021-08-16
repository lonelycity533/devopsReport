package com.hyc.report.report.service;

import java.util.LinkedHashMap;
import java.util.List;

public interface ReportService {
    List<LinkedHashMap<String,Object>> getReportData(String sql);
}
