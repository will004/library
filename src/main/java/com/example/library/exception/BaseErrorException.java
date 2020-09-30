package com.example.library.exception;

import org.springframework.http.HttpStatus;

public class BaseErrorException extends RuntimeException {

    public HttpStatus httpStatus;
    public String message;

    public BaseErrorException(HttpStatus httpStatus, String message){
        super(message);
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
