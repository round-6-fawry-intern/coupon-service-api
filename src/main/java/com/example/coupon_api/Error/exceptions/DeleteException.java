package com.example.coupon_api.Error.exceptions;

public  class DeleteException extends RuntimeException{
    public DeleteException() {}

    public DeleteException(String message) {
        super(message);
    }
}