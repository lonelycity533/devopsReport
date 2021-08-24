package com.hyc.report;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@MapperScan("com.hyc.report.mapper")
//@MapperScan(basePackages = {"com.hyc.report.mapper"})
@SpringBootApplication
@EnableSwagger2
public class DevopsReportApplication {
    public static void main(String[] args) {
        SpringApplication.run(DevopsReportApplication.class);
    }
}
