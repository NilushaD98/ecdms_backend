package com.ecdms.ecdms.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Nilusha Dissanayake
 * @version 1.0.0
 * @since V2.0.0
 */

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class EmailSenderErrorException extends RuntimeException{

    public EmailSenderErrorException(String message){
        super(message);
    }
}
