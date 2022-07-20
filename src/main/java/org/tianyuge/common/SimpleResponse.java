package org.tianyuge.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.httpclient.HttpStatus;


import java.util.Date;

/**
 *
 * @author guoqing.chen01@hand-china.com 2021-12-24 10:12
 * @param <T>
 */
public class SimpleResponse<T> {

    private T body;

    private String message;

    private int httpStatusCode = HttpStatus.SC_OK;

    //code   1 成功  0 失败
    private String code;

    private Date time;

    public SimpleResponse(T body, String message, int httpStatusCode, String code, Date time) {
        this.body = body;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
        this.code = code;
        this.time = time;
    }

    public SimpleResponse(T body, String message, String code, Date time) {
        this.body = body;
        this.message = message;
        this.code = code;
        this.time = new Date();
    }

    public SimpleResponse(T body) {
        this.body = body;
        this.message = null;
        this.code = "0";
        this.time = new Date();
    }

    public SimpleResponse(T body, String message, String code) {
        this.body = body;
        this.message = message;
        this.code = code;
        this.time = new Date();
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @JsonFormat(pattern = "yyyy/MM/dd")
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }
}
