package com.gonghak98.v2.report.service;

import com.gonghak98.v2.audit.application.QualificationAuditService;
import com.gonghak98.v2.audit.domain.dto.QualificationResult;
import com.gonghak98.v2.file.service.FileService;
import com.gonghak98.v2.file.service.dto.FileResponse;
import com.gonghak98.v2.report.controller.dto.ReportResponse;
import com.gonghak98.v2.report.controller.dto.ReportResponse.CreditSummaryDto;
import com.gonghak98.v2.report.controller.dto.ReportResponse.NonPassResultDto;
import com.gonghak98.v2.report.controller.dto.ReportResponse.PassResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final FileService fileService;
    private final QualificationAuditService qualificationAuditService;

    public ReportResponse getReport(String departmentName,
                                    Short entranceYear,
                                    MultipartFile file) {
        FileResponse fileResponse = fileService.getFileData(file);

        // 기이수 과목을 각 세부 영역별로 검사
        QualificationResult qualificationResult = qualificationAuditService.getQualificationAudit(departmentName, entranceYear,
                                                                                                  fileResponse.toCompletedCourses());

        return ReportResponse.builder()
                             .passResults(qualificationResult.passResults().entrySet().stream().map(
                                 e -> PassResultDto.from(e.getKey(), e.getValue())).toList()
                             )
                             .nonPassResults(
                                 qualificationResult.nonPassResults().stream().map(NonPassResultDto::from).toList()
                             )
                             .creditSummaries(
                                 qualificationResult.creditSummaries().entrySet().stream().map(e -> CreditSummaryDto.from(e.getKey(), e.getValue())).toList()
                             )
                             .build();
    }
}
