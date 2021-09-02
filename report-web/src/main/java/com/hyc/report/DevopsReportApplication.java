package com.hyc.report;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.ProcessIdUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.sql.DataSource;
import java.util.TimeZone;

@Slf4j
@MapperScan("com.hyc.report.mapper")
@SpringBootApplication
public class DevopsReportApplication {
    public static void main(String[] args) {
        log.info("*****报表系统正在启动*****");
        SpringApplication.run(DevopsReportApplication.class);
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        log.info("*****报表系统启动成功*****");
    }
}
