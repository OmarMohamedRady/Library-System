package com.library.librarysystem.util.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
@Getter
public class GeneralException extends RuntimeException {

    public GeneralException( String message) {
        super(String.format("Error Happened (%s)", message));
    }
}

