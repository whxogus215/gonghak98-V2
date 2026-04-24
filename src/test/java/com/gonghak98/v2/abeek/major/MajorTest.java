package com.gonghak98.v2.abeek.major;

import static org.assertj.core.api.Assertions.assertThat;

import com.gonghak98.v2.abeek.fixture.GivenObjectFixture;
import com.gonghak98.v2.fake.TestRequirementRule;
import com.gonghak98.v2.report.domain.abeek.AbeekType;
import com.gonghak98.v2.report.domain.abeek.dto.AreaCheckResult;
import com.gonghak98.v2.report.domain.abeek.major.Major;
import com.gonghak98.v2.report.domain.abeek.rule.Rule;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class MajorTest {

    @Nested
    class 전자정보통신공학과 {

        @DisplayName("전공영역은 설계, 실험, 일반, 선후수 세부요건을 검사할 수 있다.")
        @Test
        void 전공영역_검사() {
            //given
            List<CompletedCourse> completedCourses = GivenObjectFixture.createCompletedCoursesWithThreeCredit(5, AbeekType.MAJOR);
            List<Rule> rules = List.of(new TestRequirementRule());
            Major major = new Major(rules, 3 * 5);

            AreaCheckResult areaCheckResult = GivenObjectFixture.createCheckResult();

            //when
            major.checkAllCourses(completedCourses, areaCheckResult);

            //then
            assertThat(areaCheckResult.passResults().get(AbeekType.MAJOR)).isTrue();
        }
    }
}
