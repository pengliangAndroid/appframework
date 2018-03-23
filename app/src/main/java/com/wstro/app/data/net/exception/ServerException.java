package com.wstro.app.data.net.exception;

/**
 * @author pengl
 */
public class ServerException extends RuntimeException {
    int code;
    String message;
}