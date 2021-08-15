package com.hyc.report.dynamiconfig.entity;

import lombok.Data;
import lombok.ToString;

/**
 * @Author : JCccc
 * @CreateTime : 2019/10/22
 * @Description :
 **/
@Data
@ToString
public class DataSource {
    String datasourceId;
    String url;
    String userName;
    String passWord;
    String code;
    String databasetype;
}
