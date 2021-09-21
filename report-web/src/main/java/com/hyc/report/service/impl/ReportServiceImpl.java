package com.hyc.report.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hyc.report.entity.ReportCondition;
import com.hyc.report.entity.ReportDetail;
import com.hyc.report.mapper.ReportMapper;
import com.hyc.report.service.ReportService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;

//@Transactional(rollbackFor = ReportException.class)
@Service("ReportService")
public class ReportServiceImpl implements ReportService {

    @Resource
    private ReportMapper reportMapper;

    @Override
    public List<LinkedHashMap<String, Object>> getReportData(String sql) {
        return reportMapper.getReportData(sql);
    }

    @Override
    public Integer insertReportMain(ReportDetail reportDetail) throws Exception{
        return reportMapper.insertReportMain(reportDetail);
    }

    @Override
    public int insertReportDetail(ReportDetail reportDetail) throws Exception{
        return reportMapper.insertReportDetail(reportDetail);
    }

    @Override
    public Integer selectReportIdByName(String reportName) {
        return reportMapper.selectReportIdByName(reportName);
    }

    @Override
    public Page<ReportDetail> getReportInfo(int current, int size, String reportName) {
        Page<ReportDetail> page =PageHelper.startPage(current, size);
        reportMapper.getReportInfo(reportName);
        return page;
    }

    @Override
    public ReportDetail getQdDetailByName(String reportName) {
        return reportMapper.getQdDetailByName(reportName);
    }

    @Override
    public int updateReportDataConfig(ReportDetail reportDetail) {
        return reportMapper.updateReportDataConfig(reportDetail);
    }

    @Override
    public void deleteReportByIds(List<Integer> ids,List<Integer> delIds) {
        reportMapper.deleteReportByIds(ids,delIds);
    }

    @Override
    public ReportCondition getReportDetailInfo(int reportId) {
        return reportMapper.getReportDetailInfo(reportId);
    }

    @Override
    public void insertReportDetailCondition(ReportDetail reportDetail) {
        reportMapper.insertReportDetailCondition(reportDetail);
    }

    @Override
    public Integer getReportDetailId(String reportName) {
        return reportMapper.getReportDetailId(reportName);
    }

    @Override
    public List<Integer> getDelId(List<Integer> ids) {
        return reportMapper.getDelId(ids);
    }


}
