package com.gonghak98.v2.core.course;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode(of = "code")
public class CompletedCourse implements Comparable<CompletedCourse> {

    private final String code;

    private final String name;

    private final int year;

    private final int semester;

    private final double credit;

    @Override
    public int compareTo(CompletedCourse other) {
        if (this.year == other.year) {
            return Integer.compare(this.semester, other.semester);
        }
        return Integer.compare(this.year, other.year);
    }
}
