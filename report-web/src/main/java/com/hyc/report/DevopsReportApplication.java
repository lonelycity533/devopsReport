package com.hyc.report;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@MapperScan(basePackages = {"com.hyc.report.report.mapper","com.hyc.report.database.mapper"})
//@MapperScan(basePackages = {"com.hyc.report.database.mapper"})
@SpringBootApplication
@EnableSwagger2
public class DevopsReportApplication {
    public static void main(String[] args) {
        SpringApplication.run(DevopsReportApplication.class);
    }
}
