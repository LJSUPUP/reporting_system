package com.antra.evaluation.reporting_system.pojo.report;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ExcelFile {
    private String fileName;
    private String id;
    private Long fileSize;
    private LocalDateTime generatedTime;
    private String fileLocation;
    private String submitter;
    private String description;
}

