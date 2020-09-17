package com.antra.evaluation.reporting_system.service;

import com.antra.evaluation.reporting_system.pojo.api.ExcelRequest;
import com.antra.evaluation.reporting_system.pojo.api.MultiSheetExcelRequest;
import com.antra.evaluation.reporting_system.pojo.report.ExcelData;
import com.antra.evaluation.reporting_system.pojo.report.ExcelDataHeader;
import com.antra.evaluation.reporting_system.pojo.report.ExcelDataSheet;
import io.swagger.models.auth.In;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface ExcelService {
    InputStream getExcelBodyById(String id);
    String createOneSheet (ExcelRequest excelRequest) throws IOException;
    String createMultiSheet (MultiSheetExcelRequest excelRequest) throws IOException;
    List<String> getAll();
    boolean delete(String id);

/*    void fillSheet(ExcelDataSheet sheet, List<List<String>> rawData);
    void fillSheetByPartition(ExcelDataSheet sheet, List<List<String>> rawData, List<Integer> indices);
    }*/
}
