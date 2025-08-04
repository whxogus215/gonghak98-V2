package com.gonghak98.v2.completedcourse;

public class CompletedCourse {

    private String courseName;

    private int year;

    private int semester;

    private boolean isPassed;

    public CompletedCourse(String courseName) {
        this.courseName = courseName;
        this.isPassed = false;
    }

    public String getCourseName() {
        return courseName;
    }

    public boolean isPassed() {
        return isPassed;
    }

    public void pass() {
        isPassed = true;
    }
}
