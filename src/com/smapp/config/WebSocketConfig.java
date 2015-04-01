package com.smapp.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
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
    //public class WebSocketConfig implements WebSocketConfigurer{

    @Override public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/testApp");
    }

    /*@Bean
    public DefaultHandshakeHandler handshakeHandler() {
        return new DefaultHandshakeHandler(new TomcatRequestUpgradeStrategy());
    }*/

    @Override public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        stompEndpointRegistry.addEndpoint("/notification").withSockJS();
    }

    /*@Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.taskExecutor().corePoolSize(4).maxPoolSize(8);
    }

    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        registration.taskExecutor().corePoolSize(4).maxPoolSize(8);
    }*/


    //    @Override public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
    //        webSocketHandlerRegistry.addHandler(chatHandler(),"/hello");
    //    }

   /* @Override public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.setInterceptors(new SocketInterceptors());
    }*/

    /*@Bean
      public WebSocketHandler chatHandler() {
        return new ChatHandler();
    }*/
}
