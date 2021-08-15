package com.hyc.report.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

public interface ReportMapper {

    List<LinkedHashMap<String, Object>> getReportData(@Param("ReportSql") String sql);
}
