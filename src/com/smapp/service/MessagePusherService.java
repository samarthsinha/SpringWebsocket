package com.smapp.service;

import com.smapp.model.Message;
import com.smapp.model.OutputMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by samarth on 07/03/15.
 */
@Component
public class MessagePusherService {

    SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public MessagePusherService(SimpMessagingTemplate simpMessagingTemplate){
        this.simpMessagingTemplate=simpMessagingTemplate;
    }

    @Scheduled(fixedDelay = 5000)
    public void sendQuotes() {
        OutputMessage message = new OutputMessage(new Message(String.format("Hey there this is pushed %d", System.currentTimeMillis()), String.valueOf(Math.random()),""), new Date());
        //System.out.println("HERE: " + System.currentTimeMillis());
//  simpMessagingTemplate.convertAndSend("/topic/reply/test", message);
    }

}
