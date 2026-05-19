package com.gonghak98.v2.audit.domain.abeek;

import com.gonghak98.v2.audit.domain.constant.AbeekType;
import com.gonghak98.v2.audit.domain.dto.AbeekAreaAuditResult;
import com.gonghak98.v2.core.domain.course.CompletedCourse;
import java.util.List;

public interface AbeekAuditable {

    AbeekAreaAuditResult audit(List<CompletedCourse> courses);

    Double getRequiredCredits();

    AbeekType getAbeekType();
}
