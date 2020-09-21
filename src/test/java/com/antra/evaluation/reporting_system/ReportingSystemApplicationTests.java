package com.antra.evaluation.reporting_system;

import com.antra.evaluation.reporting_system.pojo.api.ExcelRequest;
import com.antra.evaluation.reporting_system.pojo.report.*;
import com.antra.evaluation.reporting_system.service.ExcelGenerationService;
import com.antra.evaluation.reporting_system.service.ExcelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReportingSystemApplicationTests {

    @Autowired
    ExcelService excelService;

    ExcelData data = new ExcelData();


    @BeforeEach // We are using JUnit 5, @Before is replaced by @BeforeEach
    public void setUpData() {
        data.setTitle("Test book");
        data.setGeneratedTime(LocalDateTime.now());

        var sheets = new ArrayList<ExcelDataSheet>();
        var sheet1 = new ExcelDataSheet();
        sheet1.setTitle("First Sheet");

        var headersS1 = new ArrayList<ExcelDataHeader>();
        ExcelDataHeader header1 = new ExcelDataHeader();
        header1.setName("NameTest");
        //       header1.setWidth(10000);
        header1.setType(ExcelDataType.STRING);
        headersS1.add(header1);

        ExcelDataHeader header2 = new ExcelDataHeader();
        header2.setName("Age");
        //   header2.setWidth(10000);
        header2.setType(ExcelDataType.NUMBER);
        headersS1.add(header2);

        List<List<Object>> dataRows = new ArrayList<>();
        List<Object> row1 = new ArrayList<>();
        row1.add("Dawei");
        row1.add(12);
        List<Object> row2 = new ArrayList<>();
        row2.add("Dawei2");
        row2.add(23);
        dataRows.add(row1);
        dataRows.add(row2);

        sheet1.setDataRows(dataRows);
        sheet1.setHeaders(headersS1);
        sheets.add(sheet1);
        data.setSheets(sheets);

        var sheet2 = new ExcelDataSheet();
        sheet2.setTitle("second Sheet");
        sheet2.setDataRows(dataRows);
        sheet2.setHeaders(headersS1);
        sheets.add(sheet2);
    }

    @Test
    public void testExcelGeneration () throws Exception {
        File file = null;
        try {
            file = new File(excelService.createExcelFile(data));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertTrue(file != null);
    }

    @Test
    public void testDeleteFileById() throws Exception{
        String fileId = excelService.createExcelFile(data);
        Boolean res1 = excelService.delete(fileId);
        assertTrue(res1);
    }

    @Test
    public void testGetFileById() throws Exception{
        String fileId = excelService.createExcelFile(data);
        String name = excelService.getExcelNameById(fileId);
        assertTrue(name!=null);

    }


    @Test
    public void testGetBodyById() throws Exception{
        String fileId = excelService.createExcelFile(data);
        InputStream fis = excelService.getExcelBodyById(fileId);
        assertTrue(fis!=null);

    }
}
