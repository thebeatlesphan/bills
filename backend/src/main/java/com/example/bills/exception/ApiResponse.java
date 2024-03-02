package com.example.bills.exception;

import java.util.Date;

public class ApiResponse {
    private String message;
    private Object data;
    private Date timestamp;
    
    public ApiResponse(String message, Object data, Date timestamp) {
        this.message = message;
        this.data = data;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

}
