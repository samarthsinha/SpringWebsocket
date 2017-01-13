![Build Status](https://api.travis-ci.org/samarthbsb/SpringWebsocket.svg?branch=master)

***
## Configuring WebMVC and JSP resolver

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

##Configuring WebSocket related 

```java
package com.smapp.config;

package com.smapp.config;

import com.smapp.SocketInterceptors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * Created by samarth on 04/03/15.
 */
@Configuration
@EnableWebSocketMessageBroker
@EnableScheduling
@ComponentScan(basePackages = "com.smapp")
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Override public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic","/queue");
        registry.setApplicationDestinationPrefixes("/testApp");
    }

    @Override public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        stompEndpointRegistry.addEndpoint("/notification").withSockJS();
    }

    @Bean
    public SocketInterceptors socketInterceptors() {
        return new SocketInterceptors();
    }


    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.setInterceptors(socketInterceptors());
    }

    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        registration.taskExecutor().corePoolSize(8);
        registration.setInterceptors(socketInterceptors());
    }
}
```

##Controller Mapping for messages
```java
@MessageMapping("/notification")
    @SendTo("/topic/message")
    public OutputMessage sendMessage(Message message) {
        if("CONNECTED".equalsIgnoreCase(message.getType()) || "DISCONNECTED".equalsIgnoreCase(message.getType())){
            message.setMessage(String.format("%s %s!!",message.getName(),message.getType().toLowerCase()));
        }
        return new OutputMessage(message, new Date());
}
```

***
##Project Screenshot
<p align="center">
  <img src="https://raw.githubusercontent.com/samarthbsb/SpringWebsocket/master/web/assets/login_screen.jpeg" width="350"/>
   <img src="https://raw.githubusercontent.com/samarthbsb/SpringWebsocket/master/web/assets/chat_window.jpeg" width="350"/>
</p>

