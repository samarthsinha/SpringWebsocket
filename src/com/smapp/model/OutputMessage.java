package com.smapp.model;

import java.util.Date;

/**
 * Created by samarth on 04/03/15.
 */
public class OutputMessage extends Message {

    private Date time;

    public OutputMessage(Message original, Date time) {
        super(original.getMessage(), original.getId(), original.getName());
        this.time = time;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
