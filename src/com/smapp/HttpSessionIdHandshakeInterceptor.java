package com.smapp;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by samarth on 07/03/15.
 */
public class HttpSessionIdHandshakeInterceptor implements HandshakeInterceptor {

    @Override public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> stringObjectMap)
            throws Exception {
        if(serverHttpRequest instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) serverHttpRequest;
            HttpSession session = ((ServletServerHttpRequest) serverHttpRequest).getServletRequest().getSession(false);
            if(session != null) {
                stringObjectMap.put("simpSessionId", session.getId());
            }
        }
        return true;
    }

    @Override public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {

    }
}
