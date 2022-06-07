package com.currency.comparator.model.exception;

public class ExceptionValidateCurrency extends IllegalArgumentException{
    public ExceptionValidateCurrency(String exception, Throwable e) {
        super(exception, e);
    }
}
