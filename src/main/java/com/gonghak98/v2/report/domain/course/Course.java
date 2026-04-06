package com.gonghak98.v2.report.domain.course;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Course {

    private String name;

    private String code;

    private double credit;

    public boolean isEqual(String code) {
        return this.code.equals(code);
    }
}
