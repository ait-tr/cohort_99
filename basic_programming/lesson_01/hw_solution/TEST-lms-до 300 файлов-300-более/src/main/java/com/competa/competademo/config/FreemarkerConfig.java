package com.competa.competademo.config;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import org.springframework.context.annotation.Bean;

/**
 * @author Siedakov Serhii
 * created on 23.01.2024
 */
@org.springframework.context.annotation.Configuration
public class FreemarkerConfig {
    @Bean
    public Configuration freemarkerConfiguration(){
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_21);
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateLoader(new ClassTemplateLoader(FreemarkerConfig.class,"/mails/"));

        return configuration;
    }
}
