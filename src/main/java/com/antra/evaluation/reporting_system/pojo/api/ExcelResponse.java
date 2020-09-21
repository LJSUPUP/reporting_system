package com.antra.evaluation.reporting_system.pojo.api;

import com.antra.evaluation.reporting_system.pojo.report.ExcelFile;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class ExcelResponse{

    private String message;
    private List<ExcelVO> data;
    public ExcelResponse(){
        super();
    }
    public ExcelResponse(List<ExcelVO> l){
        this.data = l;
    }
    public List<ExcelVO> getData() {
        return data;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public void setData(List<ExcelVO> files) {
        this.data = files;
    }
    public void updateBody(ExcelVO t) {
        data.add(t);
    }

}
