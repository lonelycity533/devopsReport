package com.hyc.report.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.Page;
import com.hyc.report.entity.ReportDatabase;
import com.hyc.report.entity.ReportDetail;

import java.util.LinkedHashMap;
import java.util.List;

public interface ReportService {
    List<LinkedHashMap<String,Object>> getReportData(String sql);

    int insertReportMain(ReportDetail reportDetail);

    int insertReportDetail(ReportDetail reportDetail);

    int selectReportIdByName(String reportName);

    Page<ReportDetail> getReportInfo(int current, int size, String reportName);

    ReportDetail getQdDetailByName(String reportName);

    int updateReportDataConfig(ReportDetail reportDetail);
}
