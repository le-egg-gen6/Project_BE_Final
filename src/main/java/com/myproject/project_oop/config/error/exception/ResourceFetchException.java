package com.myproject.project_oop.config.error.exception;

public class ResourceFetchException extends RuntimeException {

    public ResourceFetchException() {
        super();
    }

    public ResourceFetchException(String s) {
        super(s);
    }

    public ResourceFetchException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceFetchException(Throwable cause) {
        super(cause);
    }
}

