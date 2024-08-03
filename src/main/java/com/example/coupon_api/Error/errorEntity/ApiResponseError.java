package com.example.coupon_api.Error.errorEntity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ApiResponseError {
    private boolean success;
    private String message;
    private List<String> details;
    private LocalDateTime dateTime;

    private int statusCode;

    public ApiResponseError() {
    }

    public ApiResponseError(String message, List<String> details,int statusCode) {
        this.message = message;
        this.details = details;
        this.success= Boolean.FALSE;
        this.dateTime = LocalDateTime.now();
        this.statusCode= statusCode;
    }

}
