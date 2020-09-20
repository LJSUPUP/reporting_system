package com.antra.evaluation.reporting_system.pojo.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ExcelDataHeader {
    private String name;
    private ExcelDataType type;
    private int width;
}
