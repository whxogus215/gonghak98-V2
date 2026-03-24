package com.gonghak98.v2.report.domain.course;

import lombok.Builder;
import lombok.Getter;

@Builder
public class Course {

    @Getter
    private Long id;

    private String name;

    @Getter
    private double point;

    public boolean isEqual(Long id) {
        return this.id.equals(id);
    }
}
