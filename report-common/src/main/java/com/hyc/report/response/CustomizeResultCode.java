package com.hyc.report.response;

/**
 * 获取用户错误接口
 * */
public interface CustomizeResultCode {
    /**
     * 获取错误状态码
     * @return 错误状态码
     */
    Integer getCode();

    /**
     * 获取错误信息
     * @return 错误信息
     */
    String getMessage();

}
