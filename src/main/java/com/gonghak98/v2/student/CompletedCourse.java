package com.gonghak98.v2.student;

import lombok.Builder;
import lombok.Getter;

@Builder
public class CompletedCourse {

    @Getter
    private int id;

    @Getter
    private String name;

    private int year;

    private int semester;

    @Getter
    private boolean isPassed;

    @Getter
    private double point;

    public void pass() {
        isPassed = true;
    }
}
