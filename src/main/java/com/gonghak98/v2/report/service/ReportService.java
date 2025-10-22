package com.gonghak98.v2.report.service;

import com.gonghak98.v2.file.service.FileService;
import com.gonghak98.v2.file.service.dto.FileResponse;
import com.gonghak98.v2.report.controller.dto.ReportResponse;
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
    private final ReportRepository reportRepository;

    public ReportResponse createReport(String departmentName, MultipartFile file) {
        FileResponse fileResponse = fileService.getFileData(file);
        List<CompletedCourse> completedCourses = fileResponse.toCompletedCourses();

        Abeek abeek = abeekService.getAbeek(departmentName);
        CheckResult checkResult = abeek.checkAllCourses(completedCourses);

        //TODO Abeek 조회 구현 후, 구현하기!
//        Report report = Report.of(checkResult);
//
//        reportRepository.save(report);
        return null;
    }
}
