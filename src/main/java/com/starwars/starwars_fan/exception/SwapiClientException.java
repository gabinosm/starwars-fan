package com.starwars.starwars_fan.exception;

public class SwapiClientException extends RuntimeException {

    public SwapiClientException(String message) {
        super(message);
    }

    public SwapiClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
