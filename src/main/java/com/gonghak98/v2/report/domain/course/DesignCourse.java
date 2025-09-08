package com.gonghak98.v2.report.domain.course;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
public class DesignCourse {

    private final Course course;

    @Getter
    private final double designPoint;

    public boolean isEqual(int id) {
        return course.isEqual(id);
    }
}
