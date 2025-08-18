package com.gonghak98.v2.student;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CompletedCourse {

    private int id;

    private String name;

    private int year;

    private int semester;

    private double point;
}
