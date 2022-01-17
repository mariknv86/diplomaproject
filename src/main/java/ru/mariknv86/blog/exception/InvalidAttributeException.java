package ru.mariknv86.blog.exception;

import java.util.Map;

public class InvalidAttributeException extends RuntimeException {

    private static final String INVALID_ATTR_MSG = "Invalid attribute";
    private Map<String, String> errors;

    public InvalidAttributeException(Map<String, String> errors) {
        super(INVALID_ATTR_MSG);
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
