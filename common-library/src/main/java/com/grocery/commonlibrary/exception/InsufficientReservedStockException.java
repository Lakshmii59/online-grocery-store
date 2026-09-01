package com.grocery.commonlibrary.exception;

public class InsufficientReservedStockException extends RuntimeException {
    public InsufficientReservedStockException(String message) {
        super(message);
    }
}
