package com.mikail.weather.exception;

import java.net.http.HttpHeaders;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.NotNull;

@RestControllerAdvice
public class GeneralExceptionAdvice extends ResponseEntityExceptionHandler {
    
  /*   @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex
    ,@NotNull HttpHeaders header
    , @NotNull HttpStatusCode statusCode
    ,@NotNull WebRequest request){
        Map<String ,String > errors=new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error->{
            String fieldName=((FieldError)error).getField();
        String errorMessage=error.getDefaultMessage();
        errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
        }*/
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<String > handle( ConstraintViolationException exception){
    return new ResponseEntity<>(exception.getMessage(),HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(RequestNotPermitted.class)
  public ResponseEntity<String > handle(RequestNotPermitted exception){
    return new ResponseEntity<>("Request Limit Exceeded",HttpStatus.TOO_MANY_REQUESTS);
  }

}
