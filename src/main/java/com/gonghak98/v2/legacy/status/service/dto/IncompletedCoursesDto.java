package com.gonghak98.v2.legacy.status.service.dto;

import com.gonghak98.v2.legacy.common.constant.CourseCategory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class IncompletedCoursesDto {
    private final String courseName;
    private final CourseCategory courseCategory;
    private final int credit;
    private final double designCredit;
}
