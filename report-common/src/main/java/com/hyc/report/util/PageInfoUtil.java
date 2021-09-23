package com.hyc.report.util;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import java.util.LinkedHashMap;
import java.util.List;

public class PageInfoUtil {
    public static PageInfo getPageInfoBylist(List<LinkedHashMap<String,Object>> list, int pageSize,
                                             Integer pageIndex ) {
        Page page = new Page(pageIndex, pageSize);
        int total = list.size();
        page.setTotal(total);
        int startIndex = (pageIndex - 1) * pageSize;
        int endIndex = Math.min((startIndex + pageSize), total);
        page.addAll(list.subList(startIndex, endIndex));
        PageInfo pageInfo = new PageInfo(page);
        return pageInfo;
    }
}
