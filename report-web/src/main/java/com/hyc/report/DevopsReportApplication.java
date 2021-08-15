package com.hyc.report;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

//@ComponentScan(basePackages = {"com.hyc.report.mapper"})
@MapperScan("com.hyc.report.mapper")
@SpringBootApplication
public class DevopsReportApplication {
    public static void main(String[] args) {
        SpringApplication.run(DevopsReportApplication.class);
    }
}
