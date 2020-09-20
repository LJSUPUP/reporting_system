package com.antra.evaluation.reporting_system.pojo.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExcelVO {
    private String fileName;
    private String fileId;
    private Long fileSize;
    private LocalDateTime generatedTime;
    private String submitter;
    private String description;
}
