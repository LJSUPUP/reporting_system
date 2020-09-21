package com.antra.evaluation.reporting_system.exception;

import com.antra.evaluation.reporting_system.pojo.api.ExcelResponse;
import com.antra.evaluation.reporting_system.pojo.api.ResultCode;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExcelResponse> handlerException(Exception e) {
        ExcelResponse response = new ExcelResponse();
        response.setMessage(e.getMessage());
        log.error(response.getMessage());
        return new ResponseEntity<> (response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
