package com.smapp.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by samarth on 06/03/15.
 */
@Component
public class PathMatchingInterceptor extends WebContentInterceptor {

    @Override public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException {
        System.out.println("PRE: " + request.getRequestURI()+" "+request.getHeaderNames().toString());
        return super.preHandle(request, response, handler);
    }

    @Override public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("POST: " + request.getRequestURI()+" "+request.getHeaderNames());
        super.postHandle(request, response, handler, modelAndView);
    }
}
