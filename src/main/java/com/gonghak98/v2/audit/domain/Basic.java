package com.gonghak98.v2.audit.domain;

import com.gonghak98.v2.audit.domain.constant.AbeekType;
import com.gonghak98.v2.audit.domain.dto.AuditResult;
import com.gonghak98.v2.audit.domain.rule.RequirementRule;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Basic implements Auditable {

    private final AbeekType abeekType;
    private final List<RequirementRule> rules;
    private final double minCredit;

    @Override
    public AuditResult audit(List<CompletedCourse> courses) {
        boolean isSatisfied = rules.stream()
                                   .allMatch(rule -> rule.isSatisfied(courses));
        return new AuditResult(Map.of(abeekType, isSatisfied), Collections.emptyList());
    }

    @Override
    public Double getRequiredCredits() {
        return minCredit;
    }

    @Override
    public AbeekType getAbeekType() {
        return abeekType;
    }
}
