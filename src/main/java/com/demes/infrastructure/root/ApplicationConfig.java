package com.demes.infrastructure.root;

import com.demes.infrastructure.PropertySourceConfig;
import com.demes.infrastructure.root.db.JpaPersistenceConfig;
import com.demes.infrastructure.root.security.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.net.ssl.SSLSocketFactory;
import java.util.Properties;

@Configuration
@Import({SecurityConfig.class, JpaPersistenceConfig.class, PropertySourceConfig.class})
@EnableCaching
@EnableAspectJAutoProxy
@ComponentScan(value = "com.demes", excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com\\.demes\\.((infrastructure)|(web))\\..*"))
public class ApplicationConfig {

    @Autowired
    private Environment environment;

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager();
    }

    @Bean
    public JavaMailSender javaMailSenderImpl() {
        JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
        mailSenderImpl.setHost(environment.getProperty("smtp.host"));
        mailSenderImpl.setPort(environment.getProperty("smtp.port", Integer.class));
        mailSenderImpl.setProtocol(environment.getProperty("smtp.protocol"));
        mailSenderImpl.setUsername(environment.getProperty("smtp.username"));
        mailSenderImpl.setPassword(environment.getProperty("smtp.password"));
        Properties javaMailProps = new Properties();
        javaMailProps.put("mail.smtp.auth", true);
        javaMailProps.put("mail.smtp.starttls.enable", true);
        javaMailProps.put("mail.smtp.starttls.required", true);
        javaMailProps.put("mail.smtp.socketFactory.class", SSLSocketFactory.class);
        mailSenderImpl.setJavaMailProperties(javaMailProps);
        return mailSenderImpl;
    }

}
