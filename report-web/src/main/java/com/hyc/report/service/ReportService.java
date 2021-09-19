package com.hyc.report.service;

import com.github.pagehelper.Page;
import com.hyc.report.entity.ReportCondition;
import com.hyc.report.entity.ReportDetail;

import java.util.LinkedHashMap;
import java.util.List;

public interface ReportService {
    List<LinkedHashMap<String,Object>> getReportData(String sql);

    Integer insertReportMain(ReportDetail reportDetail) throws Exception;

    int insertReportDetail(ReportDetail reportDetail) throws Exception;

    Integer selectReportIdByName(String reportName);

    Page<ReportDetail> getReportInfo(int current, int size, String reportName);

    ReportDetail getQdDetailByName(String reportName);

    int updateReportDataConfig(ReportDetail reportDetail);

    void deleteReportByIds(List<Integer> ids,List<Integer> delIds);

    ReportCondition getReportDetailInfo(int reportId);

    void insertReportDetailCondition(ReportDetail reportDetail);

    Integer getReportDetailId(String reportName);

    List<Integer> getDelId(List<Integer> ids);
}
