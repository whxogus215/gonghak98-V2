package com.gonghak98.v2.audit.domain.dto;

import com.gonghak98.v2.audit.domain.constant.AbeekType;
import com.gonghak98.v2.core.domain.course.CompletedCourse;
import lombok.Builder;

@Builder
public record AuditCompletedCourse(String code,
                                   String name,
                                   int year,
                                   int semester,
                                   double credit,
                                   double designCredit,
                                   AbeekType abeekType) implements Comparable<AuditCompletedCourse> {

    public static AuditCompletedCourse from(CompletedCourse completedCourse,
                                            AbeekType abeekType,
                                            double designCredit) {
        return AuditCompletedCourse.builder()
                                   .code(completedCourse.getCode())
                                   .name(completedCourse.getName())
                                   .year(completedCourse.getYear())
                                   .semester(completedCourse.getSemester())
                                   .credit(completedCourse.getCredit())
                                   .abeekType(abeekType)
                                   .designCredit(designCredit)
                                   .build();
    }

    public AbeekType abeekType() {
        if (abeekType == null) {
            return AbeekType.NONE;
        }
        return abeekType;
    }

    @Override
    public int compareTo(AuditCompletedCourse other) {
        if (this.year == other.year) {
            return Integer.compare(this.semester, other.semester);
        }
        return Integer.compare(this.year, other.year);
    }
}
