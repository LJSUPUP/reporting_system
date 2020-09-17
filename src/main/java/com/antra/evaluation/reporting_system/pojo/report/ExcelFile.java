package com.antra.evaluation.reporting_system.pojo.report;

import java.time.LocalDateTime;

public class ExcelFile {

    private String fileName;
    private String fileId;
    private Long fileSize;
    private LocalDateTime generatedTime;
    private String fileLocation;
    private String submitter;

    public ExcelFile(){}

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public void setGeneratedTime(LocalDateTime generatedTime) {
        this.generatedTime = generatedTime;
    }

    public void setSubmitter(String submitter) {
        this.submitter = submitter;
    }
}

