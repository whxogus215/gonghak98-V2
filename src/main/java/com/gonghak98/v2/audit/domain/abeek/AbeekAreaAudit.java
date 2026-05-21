package com.gonghak98.v2.audit.domain.abeek;

import com.gonghak98.v2.audit.domain.constant.AbeekType;
import com.gonghak98.v2.audit.domain.dto.AbeekAreaAuditResult;
import com.gonghak98.v2.audit.domain.dto.AuditCompletedCourse;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AbeekAreaAudit {

    private final List<AbeekAuditable> areas;

    public AbeekAreaAuditResult auditAbeekArea(List<AuditCompletedCourse> auditCompletedCourses) {
        return areas.stream()
                    .map(area -> area.audit(auditCompletedCourses))
                    .reduce(new AbeekAreaAuditResult(new EnumMap<>(AbeekType.class), new ArrayList<>()), AbeekAreaAuditResult::merge);
    }

    public Map<AbeekType, Double> getRequiredCredits() {
        Map<AbeekType, Double> requiredCredits = new EnumMap<>(AbeekType.class);
        for (AbeekAuditable area : areas) {
            requiredCredits.put(area.getAbeekType(), area.getRequiredCredits());
        }
        return requiredCredits;
    }
}
