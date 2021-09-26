package com.hyc.report.mapper;

import com.hyc.report.entity.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.LinkedHashMap;
import java.util.List;

public interface ReportMapper{

    @Select("select report_id from REPORT_QD_MAIN where report_name = #{reportName}")
    Integer selectReportIdByName(@Param("reportName") String reportName);

    /**
     * 编辑页面查询遍历出详情表的详细信息
     * */
    @Select("SELECT  m.REPORT_DESCRIBE,d.REPORT_id,d.REPORT_DETAIL_ID,d.report_name,d.database_id  " +
            "FROM REPORT_QD_DETAIL d,REPORT_QD_MAIN m  WHERE d.report_id = m.report_id and d.report_name = #{reportName}")
    ReportDetail getQdDetailByName(@Param("reportName") String reportName);

    @Select("select d.REPORT_NAME,da.DATABASE_NAME \n" +
            "from REPORT_QD_DETAIL d,REPORT_DATABASE da\n" +
            "where d.REPORT_ID=#{reportId} and d.DATABASE_ID = da.DATABASE_ID")
    ReportCondition getReportDetailInfo(@Param("reportId") int reportId);

    @Select("select business_field from REPORT_QD_BUSINESS where REPORT_DETAIL_ID = #{reportDetailId}")
    List<Business> getQdBusinessById(Integer reportDetailId);

    @Select("select SQL_TYPE,SQL_CONTENT from REPORT_QD_FIELDLIST where REPORT_DETAIL_ID = #{reportDetailId}")
    List<Field> getQdFieldById(Integer reportDetailId);

    @Select("select report_detail_id from REPORT_QD_DETAIL where report_name = #{reportName}")
    Integer getReportDetailId(String reportName);

    List<LinkedHashMap<String, Object>> getReportData(@Param("ReportSql") String sql);

    List<ReportDetail> getReportInfo(@Param("reportName") String reportName);

    Integer insertReportMain(@Param("ReportMain")ReportDetail reportDetail);

    int insertReportDetail(@Param("ReportDetail") ReportDetail reportDetail);

    int updateReportDataConfig(@Param("reportDetail") ReportDetail reportDetail);

    void deleteReportByIds(@Param("ids") List<Integer> ids,@Param("delIds")List<Integer> delIds);

    void insertReportDetailCondition(@Param("reportDetail") ReportDetail reportDetail);

    List<Integer> getDelId(@Param("ids")List<Integer> ids);

    List<BusinessInfo> getBusinessInfoList(@Param("businessList") List<String> businessList,@Param("tableName") String tableName);
}
