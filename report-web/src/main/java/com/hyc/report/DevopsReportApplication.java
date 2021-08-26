package com.hyc.report;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@MapperScan("com.hyc.report.mapper")
@Slf4j
@SpringBootApplication
@EnableSwagger2
public class DevopsReportApplication {
    public static void main(String[] args) {
        log.info("*****报表系统正在启动*****");
        SpringApplication.run(DevopsReportApplication.class);
        log.info("*****报表系统启动成功*****");
    }
}
