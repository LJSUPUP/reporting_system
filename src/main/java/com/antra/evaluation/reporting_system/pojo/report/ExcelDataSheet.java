package com.antra.evaluation.reporting_system.pojo.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExcelDataSheet {
    private String title;
    private List<ExcelDataHeader> headers;
    private List<List<Object>> dataRows;
}
