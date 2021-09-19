package com.hyc.report.util;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringConvertList {
    public static List<Map<String, Object>> toListMap(String text){
        /*String start = "[";
        String end = "]";
        int i = text.indexOf(start);*/
        List<Object> list = JSON.parseArray(text);

        List< Map<String,Object>> listw = new ArrayList<Map<String,Object>>();
        for (Object object : list){
            Map<String,Object> ageMap = new HashMap<String,Object>();
            Map <String,Object> ret = (Map<String, Object>) object;//取出list里面的值转为map
            listw.add(ret);
        }
        return listw;

    }

}
