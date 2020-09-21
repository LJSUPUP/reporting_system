package com.antra.evaluation.reporting_system.pojo.report;

import com.antra.evaluation.reporting_system.pojo.api.ExcelRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExcelData {
    private String title;
    private LocalDateTime generatedTime;
    private List<ExcelDataSheet> sheets;
    private List<String> headers;
    private List<List<String>> dataBody;
    private String submitter;
    private String splitBy;
}
