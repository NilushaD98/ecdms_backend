package com.ecdms.ecdms.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class EmailNotVerifiedException extends RuntimeException{

    public EmailNotVerifiedException(String message){
        
        super(message);
    }
}
