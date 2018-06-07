package ru.s32xlevel.util.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ApplicationException {

    public NotFoundException(String arg) {
        super(ErrorType.DATA_NOT_FOUND, "Not Found Exception", HttpStatus.UNPROCESSABLE_ENTITY, arg);
    }

}
