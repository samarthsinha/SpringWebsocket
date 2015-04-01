package com.smapp.controller;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

/**
 * Created by samarth on 06/03/15.
 */
public class ChatHandler extends TextWebSocketHandler {

    @Override public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("Connection established "+session);
        super.afterConnectionEstablished(session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws InterruptedException, IOException {
        Thread.sleep(3000); // simulated delay
        session.sendMessage(message);
    }
}
