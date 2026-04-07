package com.gonghak98.v2.report.controller;

import com.gonghak98.v2.report.controller.dto.ReportResponse;
import com.gonghak98.v2.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping
    public ResponseEntity<ReportResponse> getReport(@RequestParam String departmentName,
                                                    @RequestParam Short entranceYear,
                                                    @RequestPart MultipartFile file) {
        ReportResponse response = reportService.getReport(departmentName, entranceYear, file);
        return ResponseEntity.ok(response);
    }
}
