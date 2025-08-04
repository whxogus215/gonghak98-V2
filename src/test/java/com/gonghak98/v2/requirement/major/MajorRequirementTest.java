package com.gonghak98.v2.requirement.major;

import static org.assertj.core.api.Assertions.assertThat;

import com.gonghak98.v2.completedcourse.CompletedCourse;
import com.gonghak98.v2.requirement.constant.RequirementType;
import com.gonghak98.v2.requirement.vo.CheckResult;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class MajorRequirementTest {

    @Nested
    class 전자정보통신공학과 {

        @DisplayName("전공영역은 설계, 실험, 일반, 선후수 세부요건을 검사할 수 있다.")
        @Test
        void 전공영역_검사() {
            //given
            CompletedCourse completedCourse = new CompletedCourse("전기회로실험");
            Set<String> essentialLabCourses = Set.of("디지털논리회로", "전기회로실험", "기초광학및실험", "전자소자공정실험", "마이크로컴퓨터실험");
            int minCount = 1;
            MajorRequirement majorRequirement = new MajorRequirement(new LabMajor(essentialLabCourses, minCount));

            //when
            CheckResult checkResult = majorRequirement.check(List.of(completedCourse));

            //then
            assertThat(checkResult.passResults().get(RequirementType.LAB)).isTrue();
            assertThat(completedCourse.isPassed()).isTrue();
        }
    }
}
