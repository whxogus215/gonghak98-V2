package com.gonghak98.v2.report.controller.dto;

import com.gonghak98.v2.report.infrastructure.collection.Report;

public record ReportResponse(String id) {

    public static ReportResponse toResponse(final Report savedReport) {
        return new ReportResponse(savedReport.getId());
    }
}
