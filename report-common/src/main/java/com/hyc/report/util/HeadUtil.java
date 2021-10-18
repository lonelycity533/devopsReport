package com.hyc.report.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HeadUtil {
    public static List<String> getHead(List<LinkedHashMap<String,Object>> mapList){
        LinkedHashMap<String,Object> map = mapList.get(0);
        List<String> headList = new ArrayList<>();
        for (String key:map.keySet()) {
            headList.add(key);
        }
        return headList;
    }
}
