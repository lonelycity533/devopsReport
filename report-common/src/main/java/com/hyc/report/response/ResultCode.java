package com.hyc.report.response;
/**
 * 枚举定义状态返回码和状态返回信息
 * 调用接口实现输出返回码和返回信息
 * */
public enum ResultCode implements CustomizeResultCode{

    REPORT_DELETE_SUCCESS(200,"报表配置删除成功"),

    REPORT_DELETE_ERROR(500,"报表配置删除失败"),

    REPORT_UPDATE_ERROR(500,"报表配置修改失败"),

    REPORT_UPDATE_SUCCESS(200,"报表配置修改成功"),

    REPORT_INSERT_SUCCESS(200,"报表配置成功"),

    REPORT_QUERY_DATABASE(507,"查不到该数据库"),

    REPORT_INSERT_REPEAT(506,"报表配置名称重复"),

    REPORT_INSERT_ERROR(505,"报表配置失败，请检查参数重新配置"),

    DATABASE_DELETE_ERROR(500,"数据库配置删除失败"),

    DATABASE_DELETE_SUCCESS(200,"数据库配置删除成功"),

    DATABASE_UPDATE_ERROR(500,"数据库配置更新失败"),

    DATABASE_UPDATE_SUCCESS(200,"数据库配置更新成功"),
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
    ERROR(50000, "失败");


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
