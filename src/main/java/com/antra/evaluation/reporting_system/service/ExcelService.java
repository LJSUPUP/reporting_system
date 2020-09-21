package com.antra.evaluation.reporting_system.service;

import com.antra.evaluation.reporting_system.exception.FileNotExitException;
import com.antra.evaluation.reporting_system.pojo.api.ExcelRequest;
import com.antra.evaluation.reporting_system.pojo.api.MultiSheetExcelRequest;
import com.antra.evaluation.reporting_system.pojo.report.ExcelData;
import com.antra.evaluation.reporting_system.pojo.report.ExcelDataHeader;
import com.antra.evaluation.reporting_system.pojo.report.ExcelDataSheet;
import com.antra.evaluation.reporting_system.pojo.report.ExcelFile;
import io.swagger.models.auth.In;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface ExcelService {
    InputStream getExcelBodyById(String id) throws FileNotExitException;
    String createOneSheet (ExcelRequest excelRequest) throws Exception;
    String createMultiSheet (MultiSheetExcelRequest excelRequest) throws Exception;
    String getExcelNameById(String id);
    List<ExcelFile> getAll();
    String createExcelFile(ExcelData data) throws Exception;
    boolean delete(String id) throws FileNotExitException;
}
