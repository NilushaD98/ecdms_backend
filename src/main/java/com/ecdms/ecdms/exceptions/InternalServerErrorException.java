/*
 * Copyright (c) 2024. Property of EPIC Lanka Technologies Pvt. Ltd.
 */
package com.ecdms.ecdms.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
/**
 * Exception Class
 * @author Nilusha Dissanayake
 * @since V2.0.0
 * @version 1.0.0
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class InternalServerErrorException extends RuntimeException{

    public InternalServerErrorException(String message){
        super(message);
    }
}
