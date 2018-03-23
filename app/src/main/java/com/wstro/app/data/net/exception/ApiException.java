package com.wstro.app.data.net.exception;

/**
 * @author pengl
 */
public class ApiException extends Exception {
    public int code;
    public String message;

    public ApiException(String message,int code){
        super(message);
        this.message = message;
        this.code = code;
    }

    public ApiException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }
}