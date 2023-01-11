package com.server.datn.server.common.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;

@Configuration
@PropertySources({
        @PropertySource(value = "classpath:message/message.properties", encoding = "UTF-8"),
        @PropertySource(value = "classpath:application.properties", encoding = "UTF-8")
})
public class ResourceBundle implements InitializingBean {
    private final Environment environment;
    private static ResourceBundle instance;

    public static ResourceBundle getInstance() {
        return instance;
    }

    public ResourceBundle(Environment environment) {
        this.environment = environment;
    }

    public String getName(String key){
        return environment.getProperty(key);
    }

    @Override
    public void afterPropertiesSet() {
        instance = this;
    }
}
