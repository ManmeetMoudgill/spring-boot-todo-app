package com.manmeet.moudgill.NewTodoApplication.exceptionsHandler;

import com.manmeet.moudgill.NewTodoApplication.config.ApiResp.ApiResponse;
import com.manmeet.moudgill.NewTodoApplication.config.ApiResp.Error;
import com.manmeet.moudgill.NewTodoApplication.exceptions.ApplicationException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<?>> handleRuntimeExceptionHandler(RuntimeException ex) {
        List<Error> errors = new ArrayList<>(1);
        errors.add(Error.builder().message(ex.getMessage()).build());
        return new ResponseEntity<>(ApiResponse.builder().errors(errors).response(null).build(), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ApiResponse<?>> handleMissingRequestHeaderException(MissingRequestHeaderException ex) {
        List<Error> errors = new ArrayList<>(1);
        errors.add(Error.builder().message(ex.getMessage()).build());
        return new ResponseEntity<>(ApiResponse.builder().errors(errors).response(null).build(), HttpStatus.FORBIDDEN);

    }




    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<?>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        List<Error> errors = new ArrayList<>(1);
        errors.add(Error.builder().message("Request body is missing !!").build());
        return new ResponseEntity<>(ApiResponse.builder().errors(errors).response(null).build(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiResponse<?>> noResourceFoundException(NoResourceFoundException ex) {
        List<Error> errors = new ArrayList<>(1);
        errors.add(Error.builder().message("Resource not found !!").build());
        return new ResponseEntity<>(ApiResponse.builder().errors(errors).response(null).build(), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ApiResponse<?>> jwtExpiredException(ExpiredJwtException ex){
        List<Error> errors = new ArrayList<>(1);
        errors.add(Error.builder().message(ex.getMessage()).build());
        return new ResponseEntity<>(ApiResponse.builder().errors(errors).response(null).build(), HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ApiResponse<?>> handleCustomExceptionHandler(ApplicationException ex) {
        List<Error> errors = new ArrayList<>(1);
        errors.add(Error.builder().message(ex.getMessage()).build());
        return new ResponseEntity<>(ApiResponse.builder().errors(errors).response(null).build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<?>> requestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        List<Error> errors = new ArrayList<>(1);
        errors.add(Error.builder().message(ex.getMessage()).build());
        return new ResponseEntity<>(ApiResponse.builder().errors(errors).response(null).build(), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        List<Error> errorsList = new ArrayList<>();
        for (ObjectError errorObj : ex.getBindingResult().getAllErrors()) {
            errorsList.add(Error.builder().message(errorObj.getDefaultMessage()).build());
        }

        return new ResponseEntity<>(ApiResponse.builder().response(null).errors(errorsList).build(), HttpStatus.BAD_REQUEST);
    }
}
