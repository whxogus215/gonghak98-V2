package com.gonghak98.v2.audit.domain.abeek;

import com.gonghak98.v2.audit.domain.constant.AbeekType;
import com.gonghak98.v2.audit.domain.dto.AbeekAreaAuditResult;
import com.gonghak98.v2.audit.domain.dto.AuditCompletedCourse;
import com.gonghak98.v2.core.domain.course.DesignCourse;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Design implements AbeekAuditable {

    private final DesignCourse basicDesignCourse;
    private final List<DesignCourse> elementDesignCourses;
    private final List<DesignCourse> comprehensiveDesignCourses;
    private final Set<String> courseCodes;

    private final double minDesignCredit;

    public Design(DesignCourse basicDesignCourse,
                  List<DesignCourse> elementDesignCourses,
                  List<DesignCourse> comprehensiveDesignCourses,
                  double minDesignCredit) {
        this.basicDesignCourse = basicDesignCourse;
        this.elementDesignCourses = elementDesignCourses;
        this.comprehensiveDesignCourses = comprehensiveDesignCourses;
        this.minDesignCredit = minDesignCredit;

        this.courseCodes = new HashSet<>();
        if (basicDesignCourse != null) {
            this.courseCodes.add(basicDesignCourse.getCourseCode());
        }
        elementDesignCourses.forEach(c -> this.courseCodes.add(c.getCourseCode()));
        comprehensiveDesignCourses.forEach(c -> this.courseCodes.add(c.getCourseCode()));
    }

    @Override
    public AbeekAreaAuditResult audit(List<AuditCompletedCourse> auditCompletedCourses) {
        AbeekAreaAuditResult abeekAreaAuditResult = new AbeekAreaAuditResult(new EnumMap<>(AbeekType.class), Collections.emptyList());
        double designPointSum = 0.0;
        boolean isBasicPassed = false;
        boolean isComprehensivePassed = false;
        for (AuditCompletedCourse course : auditCompletedCourses) {
            if (basicDesignCourse.isEqual(course.code())) {
                isBasicPassed = true;
                designPointSum += basicDesignCourse.getDesignPoint();
            }
            for (DesignCourse elementDesignCourse : elementDesignCourses) {
                if (elementDesignCourse.isEqual(course.code())) {
                    designPointSum += elementDesignCourse.getDesignPoint();
                }
            }
            for (DesignCourse comprehensiveDesignCourse : comprehensiveDesignCourses) {
                if (comprehensiveDesignCourse.isEqual(course.code())) {
                    designPointSum += comprehensiveDesignCourse.getDesignPoint();
                    isComprehensivePassed = true;
                }
            }
        }
        boolean isAllSatisfied = isBasicPassed && isComprehensivePassed && (designPointSum >= minDesignCredit);

        abeekAreaAuditResult.passResults().put(AbeekType.DESIGN, isAllSatisfied);
        return abeekAreaAuditResult;
    }

    @Override
    public Double getRequiredCredits() {
        return minDesignCredit;
    }

    @Override
    public AbeekType getAbeekType() {
        return AbeekType.DESIGN;
    }
}
