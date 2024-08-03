package com.example.coupon_api.Error.exceptions;


public  class NotValidException extends RuntimeException{
    public NotValidException() {}

    public NotValidException(String message) {
        super(message);
    }
}