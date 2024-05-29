package com.myproject.project_oop.config.error.exception;

public class InvalidArgumentException extends RuntimeException {

    public InvalidArgumentException() {
        super();
    }

    public InvalidArgumentException(String s) {
        super(s);
    }

    public InvalidArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidArgumentException(Throwable cause) {
        super(cause);
    }

}
