package com.manmeet.moudgill.NewTodoApplication.utils;

import com.manmeet.moudgill.NewTodoApplication.config.ApiResp.ApiResponse;
import com.manmeet.moudgill.NewTodoApplication.config.ApiResp.Error;
import com.manmeet.moudgill.NewTodoApplication.exceptions.ApplicationException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ApiResponse<Object>> buildErrorResponse(HttpStatus status, String errorMessage) {
        List<Error> errors = List.of(Error.builder().message(errorMessage).build());
        return new ResponseEntity<>(ApiResponse.builder().errors(errors).response(null).build(), status);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Object>> handleRuntimeExceptionHandler(RuntimeException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ApiResponse<Object>> handleMissingRequestHeaderException(MissingRequestHeaderException ex) {
        return buildErrorResponse(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Object>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Request body is missing !!");
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiResponse<Object>> noResourceFoundException(NoResourceFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, "Resource not found !!");
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ApiResponse<Object>> jwtExpiredException(ExpiredJwtException ex) {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ApiResponse<Object>> handleCustomExceptionHandler(ApplicationException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<Object>> requestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        return buildErrorResponse(HttpStatus.NOT_ACCEPTABLE, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<Error> errorsList = ex.getBindingResult().getAllErrors().stream()
                .map(errorObj -> Error.builder().message(errorObj.getDefaultMessage()).build())
                .toList();

        return new ResponseEntity<>(ApiResponse.builder().response(null).errors(errorsList).build(), HttpStatus.BAD_REQUEST);
    }
}
