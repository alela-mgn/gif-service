package com.currency.comparator.model.exception;

public class RateValidationException extends RuntimeException {
    public RateValidationException(String exception) {
        super(exception);
    }
}
