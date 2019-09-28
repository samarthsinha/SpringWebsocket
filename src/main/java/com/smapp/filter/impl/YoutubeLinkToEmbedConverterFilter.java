package com.smapp.filter.impl;

import com.smapp.filter.OutBoundMessageFilter;
import com.smapp.model.OutputMessage;
import org.springframework.web.servlet.support.RequestContext;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by samarth on 18/02/17.
 */
public class YoutubeLinkToEmbedConverterFilter implements OutBoundMessageFilter {
    @Override
    public OutputMessage filter(OutputMessage message) {
        String msg = message.getMessage();
        Pattern pattern = Pattern.compile("(https?://www.youtube.com/watch[?]?v=)([a-zA-Z0-9]*)");
        Matcher matcher = pattern.matcher(msg);
        List<String> utubeVids = new ArrayList<>();
        String outputString = "";
        int prevEnd = 0;
        while(matcher.find()){
            utubeVids.add(matcher.group(2));
            int start = matcher.start();
            int end = matcher.end();
            String embed="<br><iframe type=\"text/html\" width=\"480\" height=\"240\" src=\"https://www.youtube.com/embed/"+matcher.group(2)+"?autoplay=0\" frameborder=\"0\"></iframe>";
            outputString = outputString + msg.substring(prevEnd,start) + embed;
            prevEnd = end;
        }
        if(outputString!=null && outputString.length()>0){
            message.setMessage(outputString);
        }
        return message;
    }
}
