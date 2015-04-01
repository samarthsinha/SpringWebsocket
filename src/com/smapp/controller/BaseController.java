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
import java.util.Date;

/**
 * Created by samarth on 04/03/15.
 */
@Controller
public class BaseController {

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String viewApplication(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
        return new OutputMessage(message, new Date());
    }

}
