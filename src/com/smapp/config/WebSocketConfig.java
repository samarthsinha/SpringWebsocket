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
