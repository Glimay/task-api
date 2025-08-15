package com.app.task_api.dto.response.exceptions;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message){
        super(message);
    }
}
