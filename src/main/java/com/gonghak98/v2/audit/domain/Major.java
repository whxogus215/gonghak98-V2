package com.gonghak98.v2.audit.domain;

import com.gonghak98.v2.audit.domain.constant.AbeekType;
import com.gonghak98.v2.audit.domain.dto.AuditResult;
import com.gonghak98.v2.audit.domain.rule.Rule;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Major implements Auditable {

    private final List<Rule> rules;
    private final double minCredit;

    @Override
    public AuditResult audit(List<CompletedCourse> courses) {
        AuditResult auditResult = new AuditResult(new EnumMap<>(AbeekType.class), Collections.emptyList());
        boolean isSatisfied = rules.stream()
                                   .allMatch(rule -> rule.isSatisfied(courses));

        double totalCredit = courses.stream()
                                    .filter(course -> (course.getAbeekType() == AbeekType.MAJOR) || (course.getAbeekType() == AbeekType.DESIGN))
                                    .mapToDouble(CompletedCourse::getCredit)
                                    .sum();

        auditResult.passResults().put(AbeekType.MAJOR, isSatisfied && (totalCredit >= minCredit));
        return auditResult;
    }

    public Double getRequiredCredits() {
        return minCredit;
    }

    @Override
    public AbeekType getAbeekType() {
        return AbeekType.MAJOR;
    }
}
