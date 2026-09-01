package com.grocery.commonlibrary.exception;

import com.grocery.commonlibrary.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto>handleProductAlreadyExistsException(ProductAlreadyExistsException ex){
        ErrorResponseDto error = ErrorResponseDto.builder()
                .errorCode("PRODUCT_ALREADY_EXISTS")
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponseDto>handleProductNotFoundException(ProductNotFoundException ex){
        ErrorResponseDto error = ErrorResponseDto.builder()
                .errorCode("PRODUCT_NOT_FOUND")
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductInactiveException.class)
    public ResponseEntity<ErrorResponseDto>handleProductInactiveException(ProductInactiveException ex){
        ErrorResponseDto error = ErrorResponseDto.builder()
                .errorCode("PRODUCT_INACTIVE")
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto>handleCategoryAlreadyExistsException(CategoryAlreadyExistsException ex){
        ErrorResponseDto error = ErrorResponseDto.builder()
                .errorCode("CATEGORY_ALREADY_EXISTS")
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorResponseDto>handleCategoryNotFoundException(CategoryNotFoundException ex){
        ErrorResponseDto error = ErrorResponseDto.builder()
                .errorCode("CATEGORY_NOT_FOUND")
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponseDto>handleCustomerNotFoundException(CustomerNotFoundException ex){
        ErrorResponseDto error = ErrorResponseDto.builder()
                .errorCode("CUSTOMER_NOT_FOUND")
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InventoryNotFoundException.class)
    public ResponseEntity<ErrorResponseDto>handleInventoryNotFoundException(InventoryNotFoundException ex){
        ErrorResponseDto error = ErrorResponseDto.builder()
                .errorCode("INVENTORY_NOT_FOUND")
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateSkuException.class)
    public ResponseEntity<ErrorResponseDto>handleDuplicateSkuException(DuplicateSkuException ex){
        ErrorResponseDto error = ErrorResponseDto.builder()
                .errorCode("DUPLICATE_SKU")
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ErrorResponseDto>handleInsufficientStockException(InsufficientStockException ex){
        ErrorResponseDto error = ErrorResponseDto.builder()
                .errorCode("INSUFFICIENT_STOCK")
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidOrderStatusException.class)
    public ResponseEntity<ErrorResponseDto>handleInvalidOrderStatusException(InvalidOrderStatusException ex){
        ErrorResponseDto error = ErrorResponseDto.builder()
                .errorCode("INVALID_ORDER_STATUS")
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponseDto>handleOrderNotFoundException(OrderNotFoundException ex){
        ErrorResponseDto error = ErrorResponseDto.builder()
                .errorCode("ORDER_NOT_FOUND")
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InsufficientReservedStockException.class)
    public ResponseEntity<ErrorResponseDto>handleInsufficientReservedStockException(InsufficientReservedStockException ex){
        ErrorResponseDto error = ErrorResponseDto.builder()
                .errorCode("INSUFFICIENT_RESERVED_STOCK")
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InventoryAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto>handleInventoryAlreadyExistsException(InventoryAlreadyExistsException ex){
        ErrorResponseDto error = ErrorResponseDto.builder()
                .errorCode("INVENTORY_ALREADY_EXISTS")
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationException(
            MethodArgumentNotValidException ex) {

        FieldError fieldError = ex.getBindingResult().getFieldError();

        String message = "Validation error";

        if (fieldError != null) {
            message = fieldError.getDefaultMessage();
        }

        ErrorResponseDto error = ErrorResponseDto.builder()
                .errorCode("VALIDATION_ERROR")
                .message(message)
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto>handleGenericException(Exception ex){
        ErrorResponseDto error = ErrorResponseDto.builder()
                .errorCode("INTERNAL_SERVER_ERROR")
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
