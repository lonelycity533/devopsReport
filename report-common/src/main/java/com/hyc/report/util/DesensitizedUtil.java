package com.hyc.report.util;

public class DesensitizedUtil {
    public static String urlReplace1(String url) {
        if (url.equals("") || url==null) {
            return "";
        }
        int i1 = url.indexOf("@"),i2 = url.lastIndexOf(":");
        return url.replace(url.substring(i1, i2),"************");
    }

    public static String passwordReplace1(String password) {
        if (password.equals("") || password==null) {
            return "";
        }
        return password.replace(password,"******");
    }
}
