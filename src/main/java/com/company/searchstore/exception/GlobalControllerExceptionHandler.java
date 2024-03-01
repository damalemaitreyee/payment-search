package com.company.searchstore.exception;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestController
@RestControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleGenericException(Exception ex) {
        if (ex instanceof HttpMessageNotReadableException) {
            return ResponseEntity
                    .badRequest()
                    .body(
                            ErrorMessage.builder()
                                    .errorCode("Client Error")
                                    .errorMessage(ex.getMessage())
                                    .build()
                    );
        }
        return ResponseEntity.internalServerError()
                .body(ErrorMessage.builder()
                        .errorCode("Server Error")
                        .errorMessage(ex.getMessage())
                        .build());
    }
}
