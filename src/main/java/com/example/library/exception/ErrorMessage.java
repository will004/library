package com.example.library.exception;

public enum ErrorMessage {
    USER_NOT_FOUND("Username does not exist"),
    BOOK_NOT_FOUND("Book does not exist"),
    BOOK_OUT_OF_STOCK("Book is out of stock"),
    TRANSACTION_NOT_FOUND("Transaction not found"),
    ALREADY_BORROW_BOOK("User already borrow the book(s)");

    private String message;

    ErrorMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
