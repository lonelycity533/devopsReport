package com.hyc.report.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.Page;
import com.hyc.report.entity.ReportCondition;
import com.hyc.report.entity.ReportDatabase;
import com.hyc.report.entity.ReportDetail;
import io.swagger.models.auth.In;

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

    void deleteReportByIds(List<Integer> ids);

    ReportCondition getReportDetailInfo(int reportId);
}
