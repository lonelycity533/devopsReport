package com.hyc.report.util;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringConvertList {
    public static List<Map<String, Object>> toListMap(String json){
        List<Object> list = JSON.parseArray(json);

        List< Map<String,Object>> listw = new ArrayList<Map<String,Object>>();
        for (Object object : list){
            Map<String,Object> ageMap = new HashMap<String,Object>();
            Map <String,Object> ret = (Map<String, Object>) object;//取出list里面的值转为map
        /*for (Entry<String, Object> entry : ret.entrySet()) {
            ageMap.put(entry.getKey());
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            listw.add(ageMap);  //添加到list集合  成为 list<map<String,Object>> 集合
        }  */
            listw.add(ret);
        }
        return listw;

    }

}
