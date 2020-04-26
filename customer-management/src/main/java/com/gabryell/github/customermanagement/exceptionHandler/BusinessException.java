package com.gabryell.github.customermanagement.exceptionHandler;

public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }

}