package com.gonghak98.v2.core.domain.course;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
public class DesignCourse {

    private final Course course;

    @Getter
    private final double designPoint;

    public boolean isEqual(String code) {
        return course.isEqual(code);
    }

    public String getCourseCode() {
        return course.getCode();
    }
}
