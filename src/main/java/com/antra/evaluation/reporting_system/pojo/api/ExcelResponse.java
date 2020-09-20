package com.antra.evaluation.reporting_system.pojo.api;

import com.antra.evaluation.reporting_system.pojo.report.ExcelFile;

import java.util.ArrayList;
import java.util.List;

public class ExcelResponse<T> {


    private String message;
    private int totalFiles;
    private List<ExcelVO> files;

    public ExcelResponse(){
        super();
        files = new ArrayList<>();
    }

    public int getTotalFiles() {
        return totalFiles;
    }

    public List<ExcelVO> getFiles() {
        return files;
    }



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setFiles(List<ExcelVO> files) {
        this.files = files;
    }
    public void updateBody(ExcelVO t) {
        files.add(t);
    }
    public void setTotalFiles(int totalFiles) {
        this.totalFiles = totalFiles;
    }


}
