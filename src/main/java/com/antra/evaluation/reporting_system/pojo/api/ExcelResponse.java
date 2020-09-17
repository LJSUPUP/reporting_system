package com.antra.evaluation.reporting_system.pojo.api;

public class ExcelResponse<T> {

    public ExcelResponse(){
        super();
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String fileId;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

}
