package com.gonghak98.v2.file.service.dto;

import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.List;

public record FileResponse(List<FileData> fileDatas) {

    public List<CompletedCourse> toCompletedCourses() {
        return fileDatas.stream()
                        .map(fileData -> CompletedCourse.builder()
                                                        .code(fileData.courseCode())
                                                        .name(fileData.courseName())
                                                        .semester(fileData.semester())
                                                        .year(fileData.year())
                                                        .point(fileData.point())
                                                        .build())
                        .toList();
    }
}
