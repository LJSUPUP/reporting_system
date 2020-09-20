package com.antra.evaluation.reporting_system.pojo.api;

import java.util.ArrayList;
import java.util.List;

public class CreateResponse {
    private String message;
    private List<String> fileIds;

    public CreateResponse(){
        super();
        fileIds = new ArrayList<>();

    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getFileIds() {
        return fileIds;
    }

    public void setFileIds(List<String> fileIds) {
        this.fileIds = fileIds;
    }
}
