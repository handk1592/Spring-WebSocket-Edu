package com.websocket.edu.domain;

import java.io.Serializable;

public class SocketUser implements Serializable {


    private static final long serialVersionUID = 1230428703458384499L;

    private String userName;
    private String message;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
