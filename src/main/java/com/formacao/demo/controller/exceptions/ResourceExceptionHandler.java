package com.formacao.demo.controller.exceptions;

import com.formacao.demo.service.exceptions.DataIntegrityException;
import com.formacao.demo.service.exceptions.ObjectNotFoundExcepetion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjectNotFoundExcepetion.class)
    public ResponseEntity<StandardErro> objectNotFound(ObjectNotFoundExcepetion e, HttpServletRequest request){
        StandardErro err = new StandardErro(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(DataIntegrityException.class)
    public ResponseEntity<StandardErro> dataIntegrity(DataIntegrityException e, HttpServletRequest request){
        StandardErro err = new StandardErro(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardErro> dataIntegrity(MethodArgumentNotValidException e, HttpServletRequest request){
        ValidationError err = new ValidationError(HttpStatus.BAD_REQUEST.value(), "Erro de validação", System.currentTimeMillis());
        for(FieldError x: e.getBindingResult().getFieldErrors()) {
            err.addError(x.getField(), x.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<StandardErro> dataIntegrityTypeEnum(HttpMessageNotReadableException e, HttpServletRequest request){
        String message = e.getMessage();
        if(e.getMessage().contains("com.formacao.demo.domain.enums.TypeTransaction")){
            message = "The type transaction is invalid, choose between [WITHDRAW, TRANSFER, DEPOSIT]";}

        StandardErro err = new StandardErro(HttpStatus.BAD_REQUEST.value(), message, System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

}
