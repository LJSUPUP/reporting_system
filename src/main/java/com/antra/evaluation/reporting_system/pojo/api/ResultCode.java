package com.antra.evaluation.reporting_system.pojo.api;

public enum ResultCode {
    SUCCESS(200, "successful"),
    NOT_FOUND(404, "not found"),
    INVALID_REQUEST(410, "invalid request"),
    INVALID_DATA(420, "invalid data format"),
    SERVER_ERROR(500,"server error"),
    ;
    private Integer code;
    private String message;

    ResultCode(Integer code, String msg) {
        this.code = code;
        this.message = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
