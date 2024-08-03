package com.example.coupon_api.Error.exceptionHandler;


import com.example.coupon_api.Error.errorEntity.ApiResponseError;
import com.example.coupon_api.Error.exceptions.NotFoundException;
import com.example.coupon_api.Error.exceptions.DuplicateException;
import com.example.coupon_api.Error.exceptions.NotValidException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex
            , HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<String> errors= new ArrayList<String>();
        for (FieldError objectError : ex.getBindingResult().getFieldErrors()) {
            errors.add(objectError.getDefaultMessage());
        }
        for(ObjectError error: ex.getBindingResult().getGlobalErrors()){
            errors.add(error.getDefaultMessage());
        }
        ApiResponseError error= new ApiResponseError(ex.toString(), errors,status.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponseError> handleNotFoundException(NotFoundException ex) {
        ApiResponseError error= new ApiResponseError(ex.getMessage(), Arrays.asList(ex.getMessage()),HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<ApiResponseError> handleDuplicateException(DuplicateException ex) {
        ApiResponseError error= new ApiResponseError(ex.getMessage(), Arrays.asList(ex.getMessage()),HttpStatus.CONFLICT.value());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }


//    @ExceptionHandler(NotFoundException.class)
//    public ResponseEntity<ApiResponseError> handleDeleteException(NotFoundException ex) {
//        ApiResponseError error= new ApiResponseError(ex.getMessage(), Arrays.asList(ex.getMessage()),HttpStatus.IM_USED.value());
//        return ResponseEntity.status(HttpStatus.IM_USED).body(error);
//    }

    @ExceptionHandler(NotValidException.class)
    public ResponseEntity<ApiResponseError> handleNotValidException(NotValidException ex) {
        ApiResponseError error= new ApiResponseError(ex.getMessage(), Arrays.asList(ex.getMessage()),HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

}
