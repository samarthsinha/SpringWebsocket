package com.smapp.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;

/**
 * Created by samarth on 04/03/15.
 */
@Configuration
@ComponentScan(basePackages = "com.smapp", excludeFilters = {
        @ComponentScan.Filter(value = Controller.class,type = FilterType.ANNOTATION),
        @ComponentScan.Filter(value = Configuration.class,type = FilterType.ANNOTATION)
})
@EnableScheduling
public class AppConfig {

}
