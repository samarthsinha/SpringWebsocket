package com.smapp;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.support.ChannelInterceptorAdapter;

import java.util.Map;

/**
 * Created by samarth on 07/03/15.
 */
public class  SocketInterceptors extends ChannelInterceptorAdapter{

    @Override public Message<?> preSend(Message<?> message, MessageChannel channel) {
        Map<String, Object> sessionHeaders = SimpMessageHeaderAccessor.getSessionAttributes(message.getHeaders());
        MessageHeaders headers = message.getHeaders();
        SimpMessageType type = (SimpMessageType) sessionHeaders.get("simpMessageType");
        String simpSessionId = (String) sessionHeaders.get("simpSessionId");
        System.out.println("WsSession " + simpSessionId + " is connected for ");
        /*if(type == SimpMessageType.CONNECT){
            Principal principal = (Principal) headers.get("simpUsr");
            System.out.println("WsSession " + simpSessionId + " is connected for user "+principal.getName());
        }else if(type == SimpMessageType.DISCONNECT){
            System.out.println("WsSession "+ simpSessionId + "is disconnected");
        }*/
        return super.preSend(message,channel);
    }
}
