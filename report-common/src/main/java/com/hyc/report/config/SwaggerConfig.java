package com.hyc.report.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        //http://ip地址:端口/项目名/swagger-ui.html#/
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("报表系统API文档") //网站标题
                .description("项目名称swagger RESTful APIs......") //网站描述
                .version("1.0") //版本
                .contact(new Contact("黄煜晨","https://gitee.com/huang_yu_chen/devops-report.git"
                        ,"1904843513@qq.com")) //联系人
                .license("The Apache License") //协议
                .licenseUrl("http://www.baidu.com") //协议url
                .build();

        return new Docket(DocumentationType.SWAGGER_2) //swagger版本
                .pathMapping("/")
                .select()
                //扫描那些controller
                .apis(RequestHandlerSelectors.basePackage("com.hyc.report.database.controller"))
                .apis(RequestHandlerSelectors.basePackage("com.hyc.report.report.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo);
    }
}

