package com.spring.tutorial.user.exception;

import org.springframework.http.HttpStatus;

public class ApiException extends Exception {
    private static final long serialVersionUID = 1L;
    private final HttpStatus httpStatus;
    private final String responseCode;
    private final String code;
    private final String error;


    public ApiException(String message, HttpStatus httpStatus, String code, String error) {
        super(message);
        this.httpStatus = httpStatus;
        this.responseCode = Integer.toString(httpStatus.value());
        this.code = code;
        this.error = error;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public String getCode() {
        return code;
    }

    public String getError() {
        return error;
    }
}
