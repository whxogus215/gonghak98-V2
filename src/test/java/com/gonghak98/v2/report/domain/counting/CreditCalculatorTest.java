package com.gonghak98.v2.report.domain.counting;

import static org.assertj.core.api.Assertions.assertThat;

import com.gonghak98.v2.audit.domain.constant.AbeekType;
import com.gonghak98.v2.audit.domain.counting.AreaCreditSummary;
import com.gonghak98.v2.audit.domain.counting.CreditCalculator;
import com.gonghak98.v2.audit.domain.counting.CreditCountResult;
import com.gonghak98.v2.audit.domain.counting.dto.CountingResult;
import com.gonghak98.v2.audit.domain.dto.AuditCompletedCourse;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CreditCalculatorTest {

    @Test
    @DisplayName("영역별 이수 과목과 요구 학점을 기반으로 계산 결과가 올바르게 생성되어야 한다")
    void calculateCreditsTest() {
        // given
        AbeekType abeekType = AbeekType.MSC;
        AuditCompletedCourse course1 = AuditCompletedCourse.builder()
                                                 .code("000001")
                                                 .name("미적분학1")
                                                 .credit(3)
                                                 .build();
        AuditCompletedCourse course2 = AuditCompletedCourse.builder()
                                                 .code("000002")
                                                 .name("일반물리1")
                                                 .credit(3)
                                                 .build();

        Map<AbeekType, List<AuditCompletedCourse>> coursesByArea = new EnumMap<>(AbeekType.class);
        coursesByArea.put(abeekType, List.of(course1, course2));

        Map<AbeekType, Double> requiredPoints = new EnumMap<>(AbeekType.class);
        requiredPoints.put(abeekType, 30.0);

        // when
        CountingResult result = CreditCalculator.calculateCredits(coursesByArea, requiredPoints);

        // then
        final AreaCreditSummary areaCreditSummary = result.creditSummaries().get(abeekType);
        final CreditCountResult creditCountResult = areaCreditSummary.getCreditCountResult();
        final List<AuditCompletedCourse> relatedCourses = areaCreditSummary.getRelatedCourses();

        assertThat(creditCountResult.requiredCredits()).isEqualTo(30.0);
        assertThat(creditCountResult.completedCredits()).isEqualTo(6.0);
        assertThat(relatedCourses).hasSize(2);
    }
}
