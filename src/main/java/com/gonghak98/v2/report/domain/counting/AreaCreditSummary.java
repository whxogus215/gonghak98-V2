package com.gonghak98.v2.report.domain.counting;

import com.gonghak98.v2.audit.domain.constant.AbeekType;
import com.gonghak98.v2.core.domain.course.CompletedCourse;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class AreaCreditSummary {

    private final AbeekType abeekType;
    private final CreditCountResult creditCountResult;
    private final List<CompletedCourse> relatedCourses;
}
