package com.currency.comparator.model.exception;

public class ValidateCurrencyException extends IllegalArgumentException{
    public ValidateCurrencyException(String exception, Throwable e) {
        super(exception, e);
    }
}
