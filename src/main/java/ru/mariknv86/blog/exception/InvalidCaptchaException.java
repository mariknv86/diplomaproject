package ru.mariknv86.blog.exception;

public class InvalidCaptchaException extends RuntimeException {

    public InvalidCaptchaException() {
    }

    public InvalidCaptchaException(String message) {
        super(message);
    }
}
