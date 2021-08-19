package com.hyc.report.response;
/**
 * 枚举定义状态返回码和状态返回信息
 * 调用接口实现输出返回码和返回信息
 * */
public enum ResultCode implements CustomizeResultCode{
    /**
     * 数据库配置添加成功
     * */
    DATABASE_INSERT_REPEAT(501,"数据库配置添加重复"),
    /**
     * 数据库配置添加成功
     * */
    DATABASE_INSERT_SUCCESS(200,"数据库配置添加成功"),
    /**
     * 数据库配置添加失败
     * */
    DATABASE_INSERT_ERROR(500,"数据库配置添加失败"),
    /**
     * 20000:"成功"
     */
    SUCCESS(20000, "成功"),
    /**
     * 20001:"失败"
     */
    ERROR(20001, "失败"),
    /**
     * 3005:"密码不正确!"
     */
    PASS_NOT_CORRECT(3005, "密码不正确!请重新尝试!"),

    /**
     * 3006:"算数异常"
     */
    ARITHMETIC_EXCEPTION(3006, "算数异常"),

    /**
     * 3007:"用户不存在"
     */
    USER_NOT_FOUND_EXCEPTION(3007, "用户不存在"),
    /**
     * 3006:"尚未登录!"
     */
    NOT_LOGIN(3006, "尚未登录!"),
    /**
     * 2005:"没有找到这一条历史信息!有人侵入数据库强制删除了!"
     */
    INTRODUCTION_NOT_FOUND(2005, "没有找到这一条历史信息!有人侵入数据库强制删除了!"),
    /**
     * 404:没有找到对应的请求路径
     */
    PAGE_NOT_FOUND(404, "你要请求的页面好像暂时飘走了...要不试试请求其它页面?"),
    /**
     * 500:服务端异常
     */
    INTERNAL_SERVER_ERROR(500, "服务器冒烟了...要不等它降降温后再来访问?"),
    /**
     * 2001:未知异常
     */
    UNKNOW_SERVER_ERROR(2001, "未知异常,请联系管理员!"),

    DEPT_NOT_FOUND_EXCEPTION(3000,"没有查找到相应部门");

    private Integer code;

    private String message;

    ResultCode(Integer code,String message){
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }


}
