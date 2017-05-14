package com.demes.infrastructure.servlet;

import com.demes.constants.Routes;
import com.demes.infrastructure.PropertySourceConfig;
import freemarker.template.utility.XmlEscape;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.AbstractTemplateViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Import({PropertySourceConfig.class})
@Configuration
@EnableWebMvc
@ComponentScan("com.demes.web")
@EnableSpringDataWebSupport
@EnableAspectJAutoProxy
public class MvcConfig extends WebMvcConfigurerAdapter {
    private static final String RESOURCES_LOCATION = "/resources/";
    private static final String RESOURCES_HANDLER = RESOURCES_LOCATION + "**";
    @Value("${freemarker.datetime_format}")
    private String freemarker_datetime_format;
    @Value("${freemarker.number_format}")
    private String freemarker_number_format;
    @Value("${freemarker.default_encoding}")
    private String freemarker_default_encoding;
    @Value("${cookie.locale_age}")
    private int localeAge;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(RESOURCES_HANDLER).addResourceLocations(RESOURCES_LOCATION);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController(Routes.NOT_FOUND_URI).setViewName(Routes.NOT_FOUND_VIEW);
        registry.addViewController(Routes.ACCESS_DENIED_URI).setViewName(Routes.ACCESS_DENIED_VIEW);
        registry.addViewController(Routes.ERROR_URI).setViewName(Routes.ERROR_VIEW);
    }
    @Bean
    public ViewResolver viewResolver() {
        AbstractTemplateViewResolver viewResolver = new FreeMarkerViewResolver();
        viewResolver.setCache(true);
        viewResolver.setPrefix("");
        viewResolver.setSuffix(".ftl");
        viewResolver.setExposeSpringMacroHelpers(true);
        viewResolver.setContentType("text/html; charset=UTF-8");
        return viewResolver;
    }

    @Bean
    public FreeMarkerConfig freemarkerConfig() {
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configurer.setTemplateLoaderPath("/WEB-INF/views/");
        configurer.setDefaultEncoding(freemarker_default_encoding);
        Properties properties = new Properties();
        properties.setProperty("datetime_format", freemarker_datetime_format);
        properties.setProperty("number_format", freemarker_number_format);
        properties.setProperty("url_escaping_charset", freemarker_default_encoding);
        configurer.setFreemarkerSettings(properties);
        Map<String, Object> map = new HashMap<>();
        map.put("xml_escape", new XmlEscape());
        configurer.setFreemarkerVariables(map);
        return configurer;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(jacksonHttpMessageConverter());
    }

    @Bean
    public MappingJackson2HttpMessageConverter jacksonHttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setPrettyPrint(true);
        return converter;
    }
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }


}
