package com.antra.evaluation.reporting_system.exception;

import com.antra.evaluation.reporting_system.pojo.api.ResultCode;

public class FileNotExitException extends Exception {
    String message;
    Integer code;
    public FileNotExitException(String message){
        this.message = message;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }
}
