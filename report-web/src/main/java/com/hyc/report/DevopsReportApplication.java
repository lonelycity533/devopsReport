package com.hyc.report;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan({"com.hyc.report.report.mapper","com.hyc.report.database.mapper"})
@SpringBootApplication
public class DevopsReportApplication {
    public static void main(String[] args) {
        SpringApplication.run(DevopsReportApplication.class);
    }
}
