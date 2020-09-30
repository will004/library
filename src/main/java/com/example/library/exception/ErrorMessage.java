package com.example.library.exception;

public enum ErrorMessage {
    USER_NOT_FOUND("Username does not exist");

    private String message;

    ErrorMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
