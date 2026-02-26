package com.gonghak98.v2.report.domain.counting;

import static org.assertj.core.api.Assertions.assertThat;

import com.gonghak98.v2.report.domain.abeek.AreaType;
import com.gonghak98.v2.report.domain.counting.dto.CountingResult;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PointCalculatorTest {

    @Test
    @DisplayName("영역별 이수 과목과 요구 학점을 기반으로 계산 결과가 올바르게 생성되어야 한다")
    void calculateCreditsTest() {
        // given
        AreaType areaType = AreaType.MSC;
        CompletedCourse course1 = CompletedCourse.builder()
                                                 .id(1)
                                                 .name("미적분학1")
                                                 .point(3)
                                                 .build();
        CompletedCourse course2 = CompletedCourse.builder()
                                                 .id(2)
                                                 .name("일반물리1")
                                                 .point(3)
                                                 .build();

        Map<AreaType, List<CompletedCourse>> coursesByArea = new EnumMap<>(AreaType.class);
        coursesByArea.put(areaType, List.of(course1, course2));

        Map<AreaType, Double> requiredPoints = new EnumMap<>(AreaType.class);
        requiredPoints.put(areaType, 30.0);

        // when
        CountingResult result = PointCalculator.calculateCredits(coursesByArea, requiredPoints);

        // then
        final AreaCreditSummary areaCreditSummary = result.creditSummaries().get(areaType);
        final PointCountResult pointCountResult = areaCreditSummary.getPointCountResult();
        final List<CompletedCourse> relatedCourses = areaCreditSummary.getRelatedCourses();

        assertThat(pointCountResult.requiredPoints()).isEqualTo(30.0);
        assertThat(pointCountResult.completedPoints()).isEqualTo(6.0);
        assertThat(relatedCourses).hasSize(2);
    }
}
