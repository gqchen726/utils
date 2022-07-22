package com.github.gqchen.common;

import org.springframework.http.HttpStatus;

/**
 * @author: guoqing.chen01@hand-china.com 2021-09-17 14:30
 **/

public class ResponseData<T> {
    HttpStatus httpStatus = HttpStatus.ACCEPTED;
    String message = "操作成功";
    T data;

    public ResponseData() {
    }

    public ResponseData(T data) {
        this.data = data;
    }


    public ResponseData(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public ResponseData(HttpStatus httpStatus, String message, T data) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.data = data;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
