package com.gonghak98.v2.report.domain.student;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode(of = "code")
public class CompletedCourse implements Comparable<CompletedCourse> {

    private String code;

    private String name;

    private int year;

    private int semester;

    private double point;

    @Override
    public int compareTo(CompletedCourse other) {
        if (this.year == other.year) {
            return Integer.compare(this.semester, other.semester);
        }
        return Integer.compare(this.year, other.year);
    }
}
