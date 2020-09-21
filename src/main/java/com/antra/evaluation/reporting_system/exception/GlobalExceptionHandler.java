package com.antra.evaluation.reporting_system.exception;

import com.antra.evaluation.reporting_system.pojo.api.ExcelResponse;
import com.antra.evaluation.reporting_system.pojo.api.ResultCode;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ExcelResponse handlerException(Exception e) {
        ResultCode resultCode;
        // 自定义异常
        if (e instanceof FileNotExitException) {
            resultCode = ResultCode.NOT_FOUND;
            resultCode.setMessage(e.getMessage());
            log.error("FileNotExitException：{}", resultCode.getMessage());
        }
        else if(e instanceof InvalidDataException){
            resultCode = ResultCode.INVALID_DATA;
            resultCode.setMessage(e.getMessage());
            log.error("InvalidDataException：{}", resultCode.getMessage());
        }
        else if(e instanceof MismatchedInputException || e instanceof JsonParseException){
            resultCode = ResultCode.INVALID_REQUEST;
            resultCode.setMessage(e.getMessage());
            log.error("InvalidDataException：{}", resultCode.getMessage());
        }
        else {

            resultCode = ResultCode.SERVER_ERROR;
            resultCode.setMessage(e.getMessage());
            log.error("common exception");
        }
        return new ExcelResponse(false, resultCode);
    }
}
