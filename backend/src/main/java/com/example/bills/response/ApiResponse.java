package com.example.bills.response;

import java.util.Date;

public class ApiResponse<T> {
  private String message;
  private T data;
  private Date timestamp;

  public ApiResponse(String message, T data, Date timestamp) {
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

  public void setData(T data) {
    this.data = data;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }
}
