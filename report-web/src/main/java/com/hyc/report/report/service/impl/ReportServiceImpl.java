package com.hyc.report.report.service.impl;

import com.hyc.report.report.mapper.ReportMapper;
import com.hyc.report.report.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

@Service("ReportService")
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportMapper reportMapper;

    @Override
    public List<LinkedHashMap<String, Object>> getReportData(String sql) {
        return reportMapper.getReportData(sql);
    }
}
