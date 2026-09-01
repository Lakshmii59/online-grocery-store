package com.grocery.commonlibrary.exception;

public class InventoryAlreadyExistsException extends RuntimeException{
    public InventoryAlreadyExistsException(String message){
        super(message);
    }
}
