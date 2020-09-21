package com.antra.evaluation.reporting_system.endpoint;

import com.antra.evaluation.reporting_system.exception.FileNotExitException;
import com.antra.evaluation.reporting_system.exception.InvalidDataException;
import com.antra.evaluation.reporting_system.pojo.api.*;
import com.antra.evaluation.reporting_system.pojo.report.ExcelFile;
import com.antra.evaluation.reporting_system.service.ExcelService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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

    @PostMapping(value = "/excel", headers = "Accept=application/json", produces = "application/json")
    @ApiOperation("Generate Excel")
    public ResponseEntity<ExcelResponse> createExcel(@RequestBody @Validated ExcelRequest request) throws Exception{
        ExcelResponse response = new ExcelResponse();;
        String fileID = excelService.createOneSheet(request);
        if(fileID == null){
            response.setMessage("Cannot generate Excel file");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response = new ExcelResponse();
        response.setMessage("create file: "+ fileID);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/excel/auto")
    @ApiOperation("Generate Multi-Sheet Excel Using Split field")
    public ResponseEntity<ExcelResponse> createMultiSheetExcel(@RequestBody @Validated MultiSheetExcelRequest request) throws Exception {
        ExcelResponse response = new ExcelResponse();
        String fileID = excelService.createMultiSheet(request);
        if(fileID == null){
            response.setMessage("Cannot generate Excel file");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.setMessage("create file: "+ fileID);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/excel")
    @ApiOperation("List all existing files")
    public ResponseEntity<ExcelResponse> listExcels() throws Exception{
        ExcelResponse response = new ExcelResponse();
        List<ExcelFile> files = excelService.getAll();
        if(files.size()==0){
            response.setMessage("No file");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.setData(files.stream().map(excelFile -> ExcelVO.builder()
                .fileId(excelFile.getId())
                .fileName(excelFile.getFileName())
                .description(excelFile.getDescription())
                .fileSize(excelFile.getFileSize())
                .submitter(excelFile.getSubmitter())
                .generatedTime(excelFile.getGeneratedTime())
                .build()).collect(Collectors.toList()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/excel/{id}/content")
    @ApiOperation(value = "download a file")
    public void downloadExcel(@PathVariable String id, HttpServletResponse response) throws Exception {
        String FileName = excelService.getExcelNameById(id);
        if(FileName == null){
            return;
        }
        InputStream fis = excelService.getExcelBodyById(id);

        response.setCharacterEncoding("UTF-8");

        response.setHeader("Content-Type", "application/vnd.ms-excel");

        response.setHeader("Content-Disposition", "attachment;filename=" + FileName);

        FileCopyUtils.copy(fis, response.getOutputStream());

        fis.close();

    }

    @DeleteMapping("/excel/{id}")
    public  ResponseEntity<ExcelResponse> deleteExcel(@PathVariable String id) throws Exception{
        var response = new ExcelResponse();
        boolean success = excelService.delete(id);
        if(!success){
            response.setMessage("cannot delete file: "+ id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.setMessage("delete file: "+ id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/excel/batch")
    @ApiOperation("Generate Multi Excels")
    public ResponseEntity<ExcelResponse> createMultiExcels(@RequestBody @Validated MultiSheetExcelRequest[] requests) throws Exception {
        ExcelResponse response = new ExcelResponse();
        response.setMessage("create files:");
        for (MultiSheetExcelRequest request : requests) {
            String fileId = null;
            if (request.getSplitBy() == null)
                fileId = excelService.createOneSheet(request);
            else
                fileId = excelService.createMultiSheet(request);
            response.setMessage(response.getMessage()+" "+fileId);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/excel/{ids}/content/batch")
    @ApiOperation(value = "download multi files")
    public void downloadMultiExcel(@PathVariable String ids,HttpServletResponse response) throws IOException, FileNotExitException {
        response.setHeader("Content-Disposition", "attachment;filename=" + LocalDate.now().toString()+".zip");
        response.setHeader("ContentType","application/zip");
        response.setCharacterEncoding("UTF-8");
        String[] str = ids.split(",");
        ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());
        for(String id:str){
            String fileLocation = excelService.getExcelNameById(id);
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
