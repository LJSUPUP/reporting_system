package com.antra.evaluation.reporting_system.pojo.api;

import com.antra.evaluation.reporting_system.pojo.report.ExcelFile;

import java.util.ArrayList;
import java.util.List;

public class ExcelResponse<T> {

    private Integer code = 200;
    private String message;
    private Boolean success = true;
    private List<ExcelVO> data;
    public ExcelResponse(){
        super();
    }
    public ExcelResponse(List<ExcelVO> l){
        this.data = l;
    }



    public ExcelResponse(Boolean success, ResultCode resultCode){
        this.success = success;
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
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
    public Integer getCode() {
        return code;
    }

    public Boolean getSuccess() {
        return success;
    }

}
