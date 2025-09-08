package com.gonghak98.v2.report.controller;

import com.gonghak98.v2.report.controller.dto.ReportResponse;
import com.gonghak98.v2.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping
    public ResponseEntity<ReportResponse> createReport(@RequestBody MultipartFile file) {
        ReportResponse response = reportService.createReport(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
