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
    private int databaseId;
    private String databaseName;
    private String databaseUrl;
    private String databaseUsername;
    private String databasePassword;
    private String databaseType;
}
