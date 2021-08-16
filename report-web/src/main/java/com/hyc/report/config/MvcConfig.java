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
        registry.addViewController("/reportForm/queryDetail").setViewName("reportForm/page-bbpz-queryDetail");
        registry.addViewController("/reportForm/qd").setViewName("reportForm/page-bbpz-qd");
        registry.addViewController("/reportForm/queryQd").setViewName("reportForm/page-bbpz-queryQd");
        registry.addViewController("/reportForm/queryTj").setViewName("reportForm/page-bbpz-queryTj");
        registry.addViewController("/reportForm/sjk").setViewName("reportForm/page-bbpz-sjk");
        registry.addViewController("/reportForm/sjkEdit").setViewName("reportForm/page-bbpz-sjkEdit");
        registry.addViewController("/reportForm/tj").setViewName("reportForm/page-bbpz-tj");
        registry.addViewController("/reportForm/jtEdit").setViewName("reportForm/page-bbpz-tjEdit");
    }
}
