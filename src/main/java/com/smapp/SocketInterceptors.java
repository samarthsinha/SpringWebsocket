package com.smapp;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * Created by samarth on 07/03/15.
 */
public class  SocketInterceptors extends ChannelInterceptorAdapter{

    public Map<String,Set<String>> sessionMap = new ConcurrentHashMap<>();


    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(message);
        // ignore non-STOMP messages like heartbeat messages
        if(sha.getCommand() == null) {
            return;
        }
        String sessionId = sha.getSessionId();

        switch(sha.getCommand()) {
            case CONNECT:
                System.out.println("STOMP Connect [sessionId: " + sessionId + "]" + sha.toString());
//                String user = sha.getNativeHeader("login").get(0);
//                Set<String> sessionSet = new HashSet<>();
//                if(sessionMap.containsKey(user) && sessionMap.get(user)!=null){
//                    sessionSet = sessionMap.get(user);
//                }
//                sessionSet.add(sessionId);
//                sessionMap.put(user,sessionSet);
                break;
            case CONNECTED:
                System.out.println("STOMP Connected [sessionId: " + sessionId + "]");
                break;
            case DISCONNECT:
                System.out.println("STOMP disconnected [sessionId: " + sessionId + "]");
                break;
            default:
                break;

        }
    }
}
