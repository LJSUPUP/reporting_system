package com.antra.evaluation.reporting_system.pojo.api;

import java.util.List;

public class BatchRequest {
    private List<String> ids;

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public List<String> getIds() {
        return ids;
    }
}
