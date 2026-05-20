package com.gonghak98.v2.audit.domain.abeek;

import com.gonghak98.v2.audit.domain.constant.AbeekType;
import com.gonghak98.v2.audit.domain.dto.AbeekAreaAuditResult;
import com.gonghak98.v2.audit.domain.dto.AuditCompletedCourse;
import com.gonghak98.v2.audit.domain.rule.Rule;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Major implements AbeekAuditable {

    private final List<Rule> rules;
    private final double minCredit;

    @Override
    public AbeekAreaAuditResult audit(List<AuditCompletedCourse> courses) {
        AbeekAreaAuditResult abeekAreaAuditResult = new AbeekAreaAuditResult(new EnumMap<>(AbeekType.class), Collections.emptyList());
        boolean isSatisfied = rules.stream()
                                   .allMatch(rule -> rule.isSatisfied(courses));

        double totalCredit = courses.stream()
                                    .filter(course -> (course.abeekType() == AbeekType.MAJOR) || (course.abeekType() == AbeekType.DESIGN))
                                    .mapToDouble(AuditCompletedCourse::credit)
                                    .sum();

        abeekAreaAuditResult.passResults().put(AbeekType.MAJOR, isSatisfied && (totalCredit >= minCredit));
        return abeekAreaAuditResult;
    }

    @Override
    public Double getRequiredCredits() {
        return minCredit;
    }

    @Override
    public AbeekType getAbeekType() {
        return AbeekType.MAJOR;
    }
}
