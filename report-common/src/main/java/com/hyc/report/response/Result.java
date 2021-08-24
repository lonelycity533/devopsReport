package com.hyc.report.response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiResponses;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Api("response输出类")
@Data
public class Result {
    @ApiModelProperty(value = "请求是否成功",example = "true")
    private Boolean success;
    @ApiModelProperty(value = "请求code",example = "20000")
    private Integer code;

    @ApiModelProperty(value = "请求信息",example = "成功")
    private String message;

    @ApiModelProperty(value = "返回response数据",example =
            "{\"data\": {\n" +
                    "        \"total\": 41,\n" +
                    "        \"records\": [\n" +
                    "            {\n" +
                    "                \"createTime\": \"2021-08-19 14:31:56\",\n" +
                    "                \"updateTime\": \"2021-08-19 14:31:56\",\n" +
                    "                \"databaseId\": 32,\n" +
                    "                \"databaseName\": \"短厅\",\n" +
                    "                \"databaseUrl\": \"sdfsdf\",\n" +
                    "                \"databaseUsername\": \"sdf\",\n" +
                    "                \"databasePassword\": \"123\",\n" +
                    "                \"databaseType\": \"mysql\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"createTime\": \"2021-08-19 14:31:57\",\n" +
                    "                \"updateTime\": \"2021-08-19 14:31:57\",\n" +
                    "                \"databaseId\": 33,\n" +
                    "                \"databaseName\": \"短厅\",\n" +
                    "                \"databaseUrl\": \"sdfsdf\",\n" +
                    "                \"databaseUsername\": \"sdf\",\n" +
                    "                \"databasePassword\": \"123\",\n" +
                    "                \"databaseType\": \"mysql\"\n" +
                    "            }]" +
                    "   }" +
                    "}")
    private Map<String,Object> data = new HashMap<>();

    /**
     * 构造方法私有化,里面的方法都是静态方法
     * 达到保护属性的作用
     */
    private Result(){

    }

    /**
     * 这里是使用链式编程
     */
    public static Result ok(){
        Result result = new Result();
        result.setSuccess(true);
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMessage(ResultCode.SUCCESS.getMessage());
        return result;
    }

    public static Result error(){
        Result result = new Result();
        result.setSuccess(false);
        result.setCode(ResultCode.ERROR.getCode());
        result.setMessage(ResultCode.ERROR.getMessage());
        return result;
    }

    /**
     * 自定义返回成功与否
     * @param success
     * @return
     */
    public Result success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public Result message(String message){
        this.setMessage(message);
        return this;
    }

    public Result code(Integer code){
        this.setCode(code);
        return this;
    }

    public Result data(String key,Object value){
        this.data.put(key,value);
        return this;
    }

    public Result data(Map<String,Object> map){
        this.setData(map);
        return this;
    }

    public Result data(int code,String message) {
        this.setCode(code);
        this.setMessage(message);
        return this;
    }
}
