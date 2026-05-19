package com.gonghak98.v2.audit.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.gonghak98.v2.audit.domain.abeek.AbeekAreaAudit;
import com.gonghak98.v2.audit.domain.abeek.AbeekAuditable;
import com.gonghak98.v2.audit.domain.constant.AbeekType;
import com.gonghak98.v2.audit.domain.dto.AbeekAreaAuditResult;
import com.gonghak98.v2.audit.domain.dto.PrerequisiteAuditResult;
import com.gonghak98.v2.audit.domain.dto.QualificationResult;
import com.gonghak98.v2.audit.domain.prerequisite.PrerequisiteAudit;
import com.gonghak98.v2.audit.domain.prerequisite.PrerequisiteAuditable;
import com.gonghak98.v2.core.domain.course.CompletedCourse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class QualificationAuditTest {

    @Test
    @DisplayName("ABEEK 영역 검사가 TRUE일 때, 선후수 검사도 만족할 경우, ABEEK 영역이 TRUE로 유지된다.")
    void ABEEK_검사_통과_선후수_검사_통과() {
        //given
        QualificationAudit qualificationAudit = new QualificationAudit(createAbeekAreaAuditWith(Boolean.TRUE),
                                                                       createPrerequisiteAuditWith(Boolean.TRUE));

        //when
        QualificationResult qualificationResult = qualificationAudit.getQualificationResult(Collections.emptyList());

        //then
        assertThat(qualificationResult.passResults().get(AbeekType.DESIGN)).isTrue();
    }

    @Test
    @DisplayName("ABEEK 영역 검사가 TRUE일 때, 선후수 검사를 만족하지 않는 경우, 해당 ABEEK 영역은 FALSE로 변경된다.")
    void ABEEK_검사_통과_선후수_검사_미통과() {
        //given
        QualificationAudit qualificationAudit = new QualificationAudit(createAbeekAreaAuditWith(Boolean.TRUE),
                                                                       createPrerequisiteAuditWith(Boolean.FALSE));

        //when
        QualificationResult qualificationResult = qualificationAudit.getQualificationResult(Collections.emptyList());

        //then
        assertThat(qualificationResult.passResults().get(AbeekType.DESIGN)).isFalse();
        assertThat(qualificationResult.passResults().get(AbeekType.MAJOR)).isFalse();
    }

    @Test
    @DisplayName("ABEEK 영역 검사가 FALSE일 때, 선후수 검사 결과와 상관없이 FALSE로 유지된다.")
    void ABEEK_검사_미통과_선후수_검사_통과() {
        //given
        QualificationAudit qualificationAudit = new QualificationAudit(createAbeekAreaAuditWith(Boolean.FALSE),
                                                                       createPrerequisiteAuditWith(Boolean.TRUE));

        //when
        QualificationResult qualificationResult = qualificationAudit.getQualificationResult(Collections.emptyList());

        //then
        assertThat(qualificationResult.passResults().get(AbeekType.DESIGN)).isFalse();
        assertThat(qualificationResult.passResults().get(AbeekType.MAJOR)).isFalse();
    }

    private static AbeekAreaAudit createAbeekAreaAuditWith(Boolean value) {
        Map<AbeekType, Boolean> abeekPassResults = new EnumMap<>(AbeekType.class);
        abeekPassResults.put(AbeekType.DESIGN, value);
        abeekPassResults.put(AbeekType.MAJOR, value);
        AbeekAreaAuditResult abeekAreaAuditResult = new AbeekAreaAuditResult(abeekPassResults, new ArrayList<>());
        return new AbeekAreaAudit(List.of(new FakeAbeekAreaAudit(abeekAreaAuditResult)));
    }

    private static PrerequisiteAudit createPrerequisiteAuditWith(Boolean value) {
        Map<AbeekType, Boolean> prerequisitePassResults = new EnumMap<>(AbeekType.class);
        prerequisitePassResults.put(AbeekType.DESIGN, value);
        PrerequisiteAuditResult designPrerequisiteAuditResult = new PrerequisiteAuditResult(prerequisitePassResults, new ArrayList<>());

        Map<AbeekType, Boolean> nonPrerequisitePassResults = new EnumMap<>(AbeekType.class);
        nonPrerequisitePassResults.put(AbeekType.MAJOR, value);
        PrerequisiteAuditResult nonDesignPrerequisiteAuditResult = new PrerequisiteAuditResult(nonPrerequisitePassResults, new ArrayList<>());

        return new PrerequisiteAudit(
            List.of(
                new FakePrerequisiteAudit(designPrerequisiteAuditResult),
                new FakePrerequisiteAudit(nonDesignPrerequisiteAuditResult)
            )
        );
    }

    @RequiredArgsConstructor
    static class FakeAbeekAreaAudit implements AbeekAuditable {

        private final AbeekAreaAuditResult result;

        @Override
        public AbeekAreaAuditResult audit(List<CompletedCourse> courses) {
            return result;
        }

        @Override
        public Double getRequiredCredits() {
            return 0.0;
        }

        @Override
        public AbeekType getAbeekType() {
            return AbeekType.NONE;
        }
    }

    @RequiredArgsConstructor
    static class FakePrerequisiteAudit implements PrerequisiteAuditable {

        private final PrerequisiteAuditResult result;

        @Override
        public PrerequisiteAuditResult audit(List<CompletedCourse> courses) {
            return result;
        }
    }
}
