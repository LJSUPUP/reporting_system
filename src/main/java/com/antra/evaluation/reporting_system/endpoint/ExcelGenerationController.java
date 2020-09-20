package com.antra.evaluation.reporting_system.endpoint;

import com.antra.evaluation.reporting_system.pojo.api.*;
import com.antra.evaluation.reporting_system.pojo.report.ExcelFile;
import com.antra.evaluation.reporting_system.service.ExcelService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
public class ExcelGenerationController {

    private static final Logger log = LoggerFactory.getLogger(ExcelGenerationController.class);

    ExcelService excelService;

    @Autowired
    public ExcelGenerationController(ExcelService excelService) {
        this.excelService = excelService;
    }

    @PostMapping("/excel")
    @ApiOperation("Generate Excel")
    public ResponseEntity<CreateResponse> createExcel(@RequestBody @Validated ExcelRequest request) throws IOException {
        CreateResponse response = new CreateResponse();
        String fileID = excelService.createOneSheet(request);
        response.setMessage("create successfully");
        response.getFileIds().add(fileID);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/excel/auto")
    @ApiOperation("Generate Multi-Sheet Excel Using Split field")
    public ResponseEntity<CreateResponse> createMultiSheetExcel(@RequestBody @Validated MultiSheetExcelRequest request) throws IOException {
        CreateResponse response = new CreateResponse();
        String fileID = excelService.createMultiSheet(request);
        response.setMessage("create successfully");
        response.getFileIds().add(fileID);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/excel")
    @ApiOperation("List all existing files")
    public ResponseEntity<ExcelResponse<ExcelFile>> listExcels() {
        var response = new ExcelResponse<ExcelFile>();
        List<ExcelFile> files = excelService.getAll();
        response.setMessage("getAll");

        response.setFiles(files.stream().map(excelFile -> ExcelVO.builder()
                .fileId(excelFile.getId())
                .fileName(excelFile.getFileName())
                .description(excelFile.getDescription())
                .fileSize(excelFile.getFileSize())
                .submitter(excelFile.getSubmitter())
                .generatedTime(excelFile.getGeneratedTime())
                .build()).collect(Collectors.toList()));
        response.setTotalFiles(files.size());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/excel/{id}/content", headers = "Accept=application/octet-stream", produces = "application/octet-stream")
    @ApiOperation(value = "download a file")
    public void downloadExcel(@PathVariable String id, HttpServletResponse response) throws IOException {
        InputStream fis = excelService.getExcelBodyById(id);
        String FileName = excelService.getExcelNameById(id);

        response.setCharacterEncoding("UTF-8");

        response.setHeader("Content-Type", "application/vnd.ms-excel");

        response.setHeader("Content-Disposition", "attachment;filename=" + FileName); // TODO: File name cannot be hardcoded here

        FileCopyUtils.copy(fis, response.getOutputStream());

        fis.close();

    }

    @DeleteMapping("/excel/{id}")
    public ResponseEntity<ExcelResponse> deleteExcel(@PathVariable String id) {
        var response = new ExcelResponse();
        excelService.delete(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/excel/batch")
    @ApiOperation("Generate Multi Excels")
    public ResponseEntity<CreateResponse> createMultiExcels(@RequestBody @Validated MultiSheetExcelRequest[] requests) throws IOException {
        CreateResponse response = new CreateResponse();

        response.setMessage("create successfully");

        for (MultiSheetExcelRequest request : requests) {
            String fileId = null;
            if (request.getSplitBy() == null)
                fileId = excelService.createOneSheet(request);
            else
                fileId = excelService.createMultiSheet(request);
            response.getFileIds().add(fileId);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/excel/batch/content/{ids}")
    @ApiOperation(value = "download multi files")
    public void downloadMultiExcel(@PathVariable String ids,HttpServletResponse response) throws IOException {
        response.setHeader("Content-Disposition", "attachment;filename=" + LocalDate.now().toString()+".zip");  // 需要编码否则中文乱码
        response.setHeader("ContentType","application/zip");
        response.setCharacterEncoding("UTF-8");
        String[] str = ids.split(",");
        ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());
        for(String id:str){
            String fileLocation = excelService.getExcelNameById(id);
            System.out.println(fileLocation);
            ZipEntry zipEntryXtv = new ZipEntry(fileLocation);
            zipOutputStream.putNextEntry(zipEntryXtv);
            InputStream fis = excelService.getExcelBodyById(id);
            int j =  0;
            byte[] buffer = new byte[1024];
            while((j = fis.read(buffer)) > 0){
                zipOutputStream.write(buffer,0,j);
            }
            fis.close();
        }
        zipOutputStream.close();
    }
}
// Log
// Exception handling
// Validation
