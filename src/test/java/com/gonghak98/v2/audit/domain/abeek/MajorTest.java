package com.gonghak98.v2.audit.domain.abeek;

import static org.assertj.core.api.Assertions.assertThat;

import com.gonghak98.v2.audit.domain.dto.AbeekAreaAuditResult;
import com.gonghak98.v2.audit.domain.dto.AuditCompletedCourse;
import com.gonghak98.v2.audit.fixture.GivenObjectFixture;
import com.gonghak98.v2.fake.TestRequirementRule;
import com.gonghak98.v2.audit.domain.constant.AbeekType;
import com.gonghak98.v2.audit.domain.rule.Rule;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("전공영역 테스트")
class MajorTest {

    @DisplayName("MAJOR와 DESIGN 영역의 기이수 과목의 총 이수 학점이 최소 학점 이상이면 전공 영역의 조건을 만족한다.")
    @Test
    void 전공_설계_영역_검사_성공() {
        //given
        List<AuditCompletedCourse> completedCourses = new ArrayList<>();
        completedCourses.addAll(GivenObjectFixture.createCompletedCoursesWithThreeCredit(3, AbeekType.MAJOR));
        completedCourses.addAll(GivenObjectFixture.createCompletedCoursesWithThreeCredit(3, AbeekType.DESIGN));

        List<Rule> rules = List.of(new TestRequirementRule());
        Major major = new Major(rules, 3 * 5);

        //when
        AbeekAreaAuditResult auditResult = major.audit(completedCourses);

        //then
        assertThat(auditResult.passResults().get(AbeekType.MAJOR)).isTrue();
    }

    @DisplayName("MAJOR와 DESIGN 영역의 기이수 과목의 총 이수 학점이 최소 학점 미만이면 전공 영역의 조건을 만족하지 못한다.")
    @Test
    void 전공_설계_영역_검사_실패() {
        //given
        List<AuditCompletedCourse> completedCourses = new ArrayList<>();
        completedCourses.addAll(GivenObjectFixture.createCompletedCoursesWithThreeCredit(1, AbeekType.MAJOR));
        completedCourses.addAll(GivenObjectFixture.createCompletedCoursesWithThreeCredit(1, AbeekType.DESIGN));

        List<Rule> rules = List.of(new TestRequirementRule());
        Major major = new Major(rules, 3 * 5);

        //when
        AbeekAreaAuditResult auditResult = major.audit(completedCourses);

        //then
        assertThat(auditResult.passResults().get(AbeekType.MAJOR)).isFalse();
    }
}
