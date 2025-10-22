package com.gonghak98.v2.report.controller;

import com.gonghak98.v2.report.controller.dto.ReportRequest;
import com.gonghak98.v2.report.controller.dto.ReportResponse;
import com.gonghak98.v2.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping
    public ResponseEntity<ReportResponse> createReport(@RequestBody ReportRequest request) {
        ReportResponse response = reportService.createReport(request.departmentName(), request.file());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
