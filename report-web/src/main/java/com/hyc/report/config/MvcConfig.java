package com.hyc.report.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class MvcConfig implements WebMvcConfigurer {

    /**
     * 定义了访问各个网页的ViewController输入网址
     * */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //view名称只能是网页名，不能写全名(包括拓展名),同时当前view的位置发生变化时，也要把前面的完整的目录写出来
        registry.addViewController("/queryDetail").setViewName("reportForm/page-bbpz-queryDetail");
        registry.addViewController("/qd").setViewName("reportForm/page-bbpz-qd");
        registry.addViewController("/queryQd").setViewName("reportForm/page-bbpz-queryQd");
        registry.addViewController("/queryTj").setViewName("reportForm/page-bbpz-queryTj");
        registry.addViewController("/sjk").setViewName("reportForm/page-bbpz-sjk");
        registry.addViewController("/sjkEdit").setViewName("reportForm/page-bbpz-sjkEdit");
        registry.addViewController("/tj").setViewName("reportForm/page-bbpz-tj");
        registry.addViewController("/tjEdit").setViewName("reportForm/page-bbpz-tjEdit");
    }
}
