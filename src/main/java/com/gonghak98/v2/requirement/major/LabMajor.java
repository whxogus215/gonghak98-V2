package com.gonghak98.v2.requirement.major;

import com.gonghak98.v2.completedcourse.CompletedCourse;
import java.util.List;
import java.util.Set;

public class LabMajor {
    
    // TODO : 과목 이름을 기준으로 비교할 경우, 과목 이름이 변경됐을 때, 대응이 어려움. -> 변경 가능성이 적은 학수번호를 기준으로 비교하기
    private Set<String> essentialLabCourseNames;

    private int minCount;

    public LabMajor(Set<String> essentialLabCourseNames, int minCount) {
        this.essentialLabCourseNames = essentialLabCourseNames;
        this.minCount = minCount;
    }

    public boolean check(List<CompletedCourse> courses) {
        int count = 0;
        for (CompletedCourse course : courses) {
            if (essentialLabCourseNames.contains(course.getName())) {
                course.pass();
                count++;
            }
        }
        return count >= minCount;
    }

}
