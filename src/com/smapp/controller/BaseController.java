package com.smapp.controller;

import com.smapp.model.Message;
import com.smapp.model.OutputMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by samarth on 04/03/15.
 */
@Controller
public class BaseController {

    private static Map<String, String> sessionToName = new HashMap<>();
    private        SecureRandom        random        = new SecureRandom();
    private String sessionId;

    @RequestMapping(value = {"","/","/index"}, method = RequestMethod.GET)
    public String viewApplication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        sessionId = request.getRequestedSessionId();
        if(!sessionToName.containsKey(sessionId)){
            sessionToName.put(sessionId,new BigInteger(130, random).toString(32));
        }
        return "index";
    }

    @RequestMapping(value = "test", method = RequestMethod.GET)
    public String TestHandler(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //        response.getWriter().write("Test");
        return "test";
    }

    @MessageMapping("/notification")
    @SendTo("/topic/message")
    public OutputMessage sendMessage(Message message) {
        if(sessionToName.containsKey(message.getId())){
            message.setName(sessionToName.get(message.getId()));
        }
        return new OutputMessage(message, new Date());
    }

}
