package com.hyc.report.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.Page;
import com.hyc.report.entity.ReportCondition;
import com.hyc.report.entity.ReportDatabase;
import com.hyc.report.entity.ReportDetail;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.LinkedHashMap;
import java.util.List;

public interface ReportMapper{

    List<LinkedHashMap<String, Object>> getReportData(@Param("ReportSql") String sql);

    int insertReportMain(@Param("ReportMain")ReportDetail reportDetail);

    int insertReportDetail(@Param("ReportDetail")ReportDetail reportDetail);

    @Select("select report_id from REPORT_QD_MAIN where report_name = #{reportName}")
    int selectReportIdByName(@Param("reportName") String reportName);

    @Select("select m.REPORT_NAME,m.REPORT_DESCRIBE,m.CREATE_TIME,m.UPDATE_TIME " +
            "from REPORT_QD_MAIN m " +
            "WHERE m.REPORT_NAME like concat(concat('%',#{reportName}),'%')")
    List<ReportDetail> getReportInfo(@Param("reportName") String reportName);

    @Select("SELECT  REPORT_id,REPORT_DETAIL_ID,field_list,business_field,report_name,database_id  " +
            "FROM REPORT_QD_DETAIL  WHERE report_name = #{reportName}")
    ReportDetail getQdDetailByName(@Param("reportName") String reportName);

    int updateReportDataConfig(@Param("reportDetail") ReportDetail reportDetail);

    void deleteReportByIds(@Param("ids") List<Integer> ids);

    @Select("select d.REPORT_NAME,d.FIELD_LIST,d.BUSINESS_FIELD,da.DATABASE_NAME \n" +
            "from REPORT_QD_DETAIL d,REPORT_DATABASE da\n" +
            "where d.REPORT_ID=#{reportId} and d.DATABASE_ID = da.DATABASE_ID")
    ReportCondition getReportDetailInfo(@Param("reportId") int reportId);
}
