package com.antra.evaluation.reporting_system.repo;

import com.antra.evaluation.reporting_system.pojo.report.ExcelFile;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ExcelRepositoryImpl implements ExcelRepository {

    Map<String, ExcelFile> excelData = new ConcurrentHashMap<>();

    @Override
    public Optional<ExcelFile> getFileById(String id) {
        return Optional.ofNullable(excelData.get(id));
    }

    @Override
    public ExcelFile saveFile(ExcelFile file) {
        excelData.put(String.valueOf(file.getId()),file);
        return null;
    }

    @Override
    public ExcelFile deleteFile(String id) {

        if(excelData.containsKey(id)){

            File file = new File(excelData.get(id).getFileLocation());
            excelData.remove(id);
            boolean flag = file.delete();
        }


        return null;
    }

    @Override
    public List<ExcelFile> getFiles() {

        return List.copyOf(excelData.values());
    }

    @Override
    public String getNameById(String id){
        if(!excelData.containsKey(id)){
            return null;
        }
        return excelData.get(id).getFileName();
    }

    @Override
    public String getLocationById(String id){
        if(!excelData.containsKey(id)){
            return null;
        }
        return excelData.get(id).getFileLocation();
    }
}

