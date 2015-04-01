package com.smapp.model;

/**
 * Created by samarth on 04/03/15.
 */
public class Message {

    private String message;
    private String id;

    public Message() {
    }

    public Message(String message, String id) {
        this.message = message;
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
