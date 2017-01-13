![Build Status](https://api.travis-ci.org/samarthbsb/SpringWebsocket.svg?branch=master)

***
### Configuring WebMVC and JSP resolver

```java
package com.smapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.mvc.WebContentInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Created by samarth on 04/03/15.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.smapp")
@Import({ WebSocketConfig.class })
public class WebConfig extends WebMvcConfigurerAdapter {


    @Bean
    public InternalResourceViewResolver getInternalResourceViewResolver() {
        InternalResourceViewResolver resourceViewResolver = new InternalResourceViewResolver();
        resourceViewResolver.setPrefix("/WEB-INF/views/");
        resourceViewResolver.setSuffix(".jsp");
        return resourceViewResolver;
    }

    @Bean
    public WebContentInterceptor webContentInterceptor() {
        WebContentInterceptor interceptor = new WebContentInterceptor();
        interceptor.setCacheSeconds(0);
        interceptor.setUseExpiresHeader(true);
        interceptor.setUseCacheControlHeader(true);
        interceptor.setUseCacheControlNoStore(true);
        return interceptor;
    }

    @Override public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(webContentInterceptor());
        //        registry.addInterceptor(new PathMatchingInterceptor());
    }

    @Override public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/libs/**").addResourceLocations("/libs/");
        registry.addResourceHandler("/app/**").addResourceLocations("/app/");
        registry.addResourceHandler("/assets/**").addResourceLocations("/assets/");

    }
}
```

