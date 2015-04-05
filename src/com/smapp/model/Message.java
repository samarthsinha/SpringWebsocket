package com.smapp.model;

/**
 * Created by samarth on 04/03/15.
 */
public class Message {

    private String message;
    private String id;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Message() {
    }

    public Message(String message, String id,String name) {
        this.message = message;
        this.id = id;
        this.name=name;
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
