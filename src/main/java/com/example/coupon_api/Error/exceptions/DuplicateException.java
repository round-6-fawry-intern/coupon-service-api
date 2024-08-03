package com.example.coupon_api.Error.exceptions;

public class DuplicateException extends RuntimeException{
    public DuplicateException() {

    }

    public DuplicateException(String message) {
        super(message);
    }
}