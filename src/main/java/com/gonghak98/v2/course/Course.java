package com.gonghak98.v2.course;

import lombok.Builder;
import lombok.Getter;

@Builder
public class Course {

    @Getter
    private int id;

    private String name;

    private double point;

    public boolean isEqual(int id) {
        return this.id == id;
    }
}
