package com.demes.infrastructure;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@PropertySources(
        {@PropertySource("classpath:spring/application.properties"),
                @PropertySource("classpath:spring/${spring.active.profiles:dev}/db.properties")}
)
@Configuration
public class PropertySourceConfig {
}
