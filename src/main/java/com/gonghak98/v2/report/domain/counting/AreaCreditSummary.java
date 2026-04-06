package com.gonghak98.v2.report.domain.counting;

import com.gonghak98.v2.report.domain.abeek.AbeekType;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class AreaCreditSummary {

    private final AbeekType abeekType;
    private final PointCountResult pointCountResult;
    private final List<CompletedCourse> relatedCourses;
}
