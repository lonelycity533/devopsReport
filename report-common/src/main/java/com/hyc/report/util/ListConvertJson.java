package com.hyc.report.util;

import com.alibaba.fastjson.JSON;

import java.util.List;
import java.util.Map;

public class ListConvertJson {
    public static String getJson(List<Map<String,Object>> list) {
        return JSON.toJSONString(list);
    }
}
