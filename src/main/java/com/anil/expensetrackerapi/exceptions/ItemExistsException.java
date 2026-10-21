package com.anil.expensetrackerapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ItemExistsException extends RuntimeException{
    public ItemExistsException(String message){
        super(message);
    }
}
