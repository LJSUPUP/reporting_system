package com.antra.evaluation.reporting_system.service;

import com.antra.evaluation.reporting_system.exception.FileNotExitException;
import com.antra.evaluation.reporting_system.exception.InvalidDataException;
import com.antra.evaluation.reporting_system.pojo.api.ExcelRequest;
import com.antra.evaluation.reporting_system.pojo.api.MultiSheetExcelRequest;
import com.antra.evaluation.reporting_system.pojo.report.*;
import com.antra.evaluation.reporting_system.repo.ExcelRepository;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ExcelServiceImpl implements ExcelService {

    @Autowired
    ExcelRepository excelRepository;

    @Autowired
    ExcelGenerationService excelGenerationService;


    private static String[] digits = new String[] { "a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z" };

    @Override
    public String createOneSheet(ExcelRequest excelRequest) throws Exception {
        ExcelData data = new ExcelData();
        initExcelData(excelRequest, data);
        List<ExcelDataHeader> headers = transHeaders(excelRequest.getHeaders());
        ExcelDataSheet sheet = new ExcelDataSheet();
        sheet.setHeaders(new ArrayList<>(headers));
        sheet.setTitle(excelRequest.getDescription());
        fillSheet(sheet, excelRequest.getData());
        data.getSheets().add(sheet);
        String fileId = null;
        fileId = createExcelFile(data);
        if(fileId==null){
            throw new InvalidDataException("cannot generate excel file");
        }
        return fileId;
    }

    @Override
    public String createMultiSheet(MultiSheetExcelRequest excelRequest) throws Exception {
        ExcelData data = new ExcelData();
        initExcelData(excelRequest, data);
        List<ExcelDataHeader> headers = transHeaders(excelRequest.getHeaders());
        Map<String, List<Integer>> map = new HashMap<>();
        int index = excelRequest.getHeaders().indexOf(excelRequest.getSplitBy());
        if(index==-1)
            throw new InvalidDataException("Excel Data Error: invalid splitBy parameter");
        int pointer = 0;
        for(List<String> dataRow: excelRequest.getData()){
            String partition = dataRow.get(index);
            if(!map.containsKey(partition)){
                map.put(partition, new ArrayList<Integer>());
            }
            map.get(partition).add(pointer++);
        }
        for(String partition: map.keySet()){
            ExcelDataSheet sheet = new ExcelDataSheet();
            sheet.setHeaders(new ArrayList<>(headers));
            sheet.setTitle(partition);
            fillSheetByPartition(sheet, excelRequest.getData(), map.get(partition));
            data.getSheets().add(sheet);
        }
        String fileId = null;
        fileId = createExcelFile(data);
        if(fileId == null)
            throw new InvalidDataException("cannot generate excel file");
        return fileId;
    }


    @Override
    public List<ExcelFile> getAll(){
        List<String> res = new ArrayList<>();
        List<ExcelFile> files = excelRepository.getFiles();

        for(ExcelFile f: files){
            res.add(String.valueOf(f.getId()));
        }
        return files;
    }

    @Override
    public InputStream getExcelBodyById(String id) throws FileNotExitException {

        Optional<ExcelFile> fileInfo = excelRepository.getFileById(id);
        if(fileInfo.isEmpty()){
            throw new FileNotExitException("cannot get excel file:"+id);
        }
        File file = new File(fileInfo.get().getFileLocation());
            try {
                return new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        return null;
    }
    @Override
    public boolean delete(String id) throws FileNotExitException {
        if(excelRepository.getNameById(id)==null){
            throw new FileNotExitException("cannot delete excel file:"+id);
        }
        excelRepository.deleteFile(id);
        return true;

    }
    private void initExcelData(ExcelRequest excelRequest, ExcelData data) throws IOException {

        data.setSheets(new ArrayList<ExcelDataSheet>());
        data.setGeneratedTime(LocalDateTime.now());
        data.setTitle(excelRequest.getDescription());
        data.setSubmitter(excelRequest.getSubmitter());
        data.setSplitBy(excelRequest.getSplitBy());
    }

    private void fillSheet(ExcelDataSheet sheet, List<List<String>> rawData){
        sheet.setDataRows(new ArrayList<List<Object>>());
        for(List<String> dataRow: rawData){
            sheet.getDataRows().add(new ArrayList<>(dataRow));
        }
    }
    private void fillSheetByPartition(ExcelDataSheet sheet, List<List<String>> rawData, List<Integer> indices){
        sheet.setDataRows(new ArrayList<List<Object>>());
        for(Integer index: indices){
            sheet.getDataRows().add(new ArrayList<>(rawData.get(index)));
        }
    }
    private List<ExcelDataHeader> transHeaders(List<String> headers){
        List<ExcelDataHeader> dataHeaders = new ArrayList<>();
        for(String headerName: headers){
            ExcelDataHeader excelDataHeader = new ExcelDataHeader();
            excelDataHeader.setName(headerName);
            excelDataHeader.setType(ExcelDataType.STRING);
            excelDataHeader.setWidth(10);
            dataHeaders.add(excelDataHeader);
        }
        return dataHeaders;
    }
    @Override
    public String getExcelNameById(String id){
        return excelRepository.getNameById(id);
    }
    private String generateShortUuid() {
        StringBuilder shortBuffer = new StringBuilder();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(digits[x % 0x3E]);
        }
        return shortBuffer.toString();
    }

    @Override
    public String createExcelFile(ExcelData data) throws Exception {
        String fileId = generateShortUuid();
        while(!excelRepository.getFileById(fileId).isEmpty()){
            fileId = generateShortUuid();
        }
        // init excel file meta data
        ExcelFile excelFile = new ExcelFile();
        // id and file name
        excelFile.setId(fileId);
        excelFile.setGeneratedTime(LocalDateTime.now());
        excelFile.setSubmitter(data.getSubmitter());
        File file = excelGenerationService.generateExcelReport(data);
        excelFile.setFileName(file.getName());
        excelFile.setFileSize(file.length());
        excelFile.setFileLocation(file.getAbsolutePath());
        excelFile.setDescription(data.getTitle());


        // save excel file
        excelRepository.saveFile(excelFile);
        return fileId;
    }

}
