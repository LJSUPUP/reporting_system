package com.antra.evaluation.reporting_system.pojo.api;

import java.util.ArrayList;
import java.util.List;

public class MultiExcelRequest {
    private List<MultiSheetExcelRequest> requestList;

    public List<MultiSheetExcelRequest> getRequestList() {
        return requestList;
    }

    public void setRequestList(List<MultiSheetExcelRequest> requestList) {
        this.requestList = requestList;
    }
}
