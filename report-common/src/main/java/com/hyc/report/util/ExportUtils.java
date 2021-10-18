package com.hyc.report.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 导出管理控制器
 * </p>
 *
 * @author Xing
 * @since 2018-07-12
 */
@Slf4j
public class ExportUtils {
    /**
     * 用户信息导出类
     */
    public static void uploadExcelAboutUser(HttpServletResponse response,String fileName,List<String> columnList,List<LinkedHashMap<String,Object>> dataList){
        //声明输出流
        OutputStream os = null;
        //设置响应头
        setResponseHeader(response,fileName);
        try {
            //获取输出流
            os = response.getOutputStream();
            //内存中保留1000条数据，以免内存溢出，其余写入硬盘
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
            //获取该工作区的第一个sheet
            HSSFSheet sheet = hssfWorkbook.createSheet();
            int excelRow = 0;
            //创建标题行
            Row titleRow = sheet.createRow(excelRow);

            for(int i = 0;i<columnList.size();i++){
                //创建该行下的每一列，并写入标题数据
                Cell cell = titleRow.createCell(i);
                cell.setCellValue(columnList.get(i));
            }
            //设置内容行
            if(dataList!=null && dataList.size()>0){
                //外层for循环创建行
                for(int i = 0;i<dataList.size();i++){
                    int lastRowNum = sheet.getLastRowNum();
                    HSSFRow dataRow = sheet.createRow(lastRowNum + 1);
                    for (int j = 0 ;j<columnList.size(); j++) {
                        log.info("i="+i+",值"+dataList.get(i).get(columnList.get(j))+"");
                        dataRow.createCell(j).setCellValue(dataList.get(i).get(columnList.get(j))+"");
                    }
                }
            }
            //将整理好的excel数据写入流中
            hssfWorkbook.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭输出流
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
        设置浏览器下载响应头
     */
    private static void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(),"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

