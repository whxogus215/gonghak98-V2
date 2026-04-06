package com.gonghak98.v2.report.service;

import com.gonghak98.v2.file.service.FileService;
import com.gonghak98.v2.file.service.dto.FileResponse;
import com.gonghak98.v2.report.controller.dto.ReportResponse;
import com.gonghak98.v2.report.controller.dto.ReportResponse.CreditSummaryDto;
import com.gonghak98.v2.report.controller.dto.ReportResponse.NonPassResultDto;
import com.gonghak98.v2.report.controller.dto.ReportResponse.PassResultDto;
import com.gonghak98.v2.report.domain.abeek.Abeek;
import com.gonghak98.v2.report.domain.abeek.dto.CheckResult;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final FileService fileService;
    private final AbeekService abeekService;

    public ReportResponse getReport(String departmentName,
                                    Short entranceYear,
                                    MultipartFile file) {
        FileResponse fileResponse = fileService.getFileData(file);
        List<CompletedCourse> completedCourses = fileResponse.toCompletedCourses();

        Abeek abeek = abeekService.getAbeek(departmentName, entranceYear);
        CheckResult checkResult = abeek.checkAllCourses(completedCourses);

        return ReportResponse.builder()
                             .passResults(checkResult.passResults().entrySet().stream().map(e -> PassResultDto.from(e.getKey(), e.getValue())).toList())
                             .nonPassResults(
                                 checkResult.nonPassResults().entrySet().stream().map(e -> NonPassResultDto.from(e.getKey(), e.getValue())).toList())
                             .creditSummaries(
                                 checkResult.creditSummaries().entrySet().stream().map(e -> CreditSummaryDto.from(e.getKey(), e.getValue())).toList())
                             .build();
    }
}
