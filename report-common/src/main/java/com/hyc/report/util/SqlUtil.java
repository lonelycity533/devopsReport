package com.hyc.report.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class SqlUtil {
    public static List<String> getTableNameBySql(String sql) {
        //获取from字符串的开始索引
        int fromIndex = sql.indexOf("from");
        //以from末尾字符的索引开始截取所有主SQL的字段长度即为from后面的一个或多个表名
        String substring = sql.substring(fromIndex + 5, sql.length());
        //定义存放table的列表
        List<String> tableList = new ArrayList<>();
        //判断SQL语句中表名是否重命名（也就是是否包含空格）
        if (substring.contains(" ")) {
            //将获取的table字符串用,隔开存入字符数组
            String[] tableArray = substring.split(",");
            //遍历字符数组
            for(int i = 0 ;i< tableArray.length;i++) {
                //获取空格在字符串中最后的位置索引，并从开始截取到该索引位置即是数据库单个表名
                String table = tableArray[i].substring(0, tableArray[i].lastIndexOf(" "));
                //将表名存入列表
                tableList.add(table);
            }
        //若无空格
        }else {
            //将获取的table字符串用,隔开存入字符数组
            String[] tableArray = substring.split(",");
            //遍历字符数组
            for(int i = 0 ;i< tableArray.length;i++) {
                //将表名存入列表
                tableList.add(tableArray[i]);
            }
        }
        return tableList;
    }

    public static String sqlCompose(String dataType,String columnName,Object dataValue) {
        String condition = "";
        if(dataType.equals("VARCHAR2")){
            condition+=columnName+" = "+"'"+dataValue+"'";
        }else if(dataType.equals("NUMBER")){
            condition+=columnName+" = "+dataValue;
        }else if (dataType.equals("DATE")){
            List<String> list = (ArrayList<String>)dataValue;
            String dateSql = columnName+" between ";
            dateSql+="to_date('"+list.get(0)+"','yyyy-mm-dd hh24:mi:ss')"+" and to_date('"+list.get(1)+"','yyyy-mm-dd hh24:mi:ss')";
            condition+=dateSql;
        }
        return condition;
    }
}
