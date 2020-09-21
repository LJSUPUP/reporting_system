package com.antra.evaluation.reporting_system.pojo.api;

import java.util.ArrayList;
import java.util.List;

public class ExcelRequest {
    private List<String> headers;
    private String description;
    private List<List<String>> data;
    private String submitter;
    private String splitBy;
    public ExcelRequest(){
        this.data = new ArrayList<>();
    }
    public String getSubmitter() {
        return submitter;
    }

    public void setSubmitter(String submitter) {
        this.submitter = submitter;
    }

    public void setData(List<List<String>> data) {
        this.data = data;
    }

    public List<List<String>> getData() {
        return data;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }
    public String getSplitBy() { return splitBy;}

    public void setSplitBy(String splitBy) { this.splitBy = splitBy; }
}
