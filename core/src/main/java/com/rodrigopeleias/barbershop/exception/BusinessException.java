package com.rodrigopeleias.barbershop.exception;

import lombok.Getter;

public class BusinessException extends RuntimeException{

    @Getter
    protected int errorCode;

    public BusinessException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
