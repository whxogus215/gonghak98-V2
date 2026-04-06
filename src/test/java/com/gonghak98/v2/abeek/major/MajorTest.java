package com.gonghak98.v2.abeek.major;

import static com.gonghak98.v2.abeek.fixture.MajorFixture.createGeneralMajor;
import static com.gonghak98.v2.abeek.fixture.MajorFixture.createLabMajor;
import static org.assertj.core.api.Assertions.assertThat;

import com.gonghak98.v2.abeek.fixture.GivenObjectFixture;
import com.gonghak98.v2.report.domain.abeek.AbeekType;
import com.gonghak98.v2.report.domain.abeek.dto.RequirementResult;
import com.gonghak98.v2.report.domain.abeek.major.Major;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class MajorTest {

    @Nested
    class 전자정보통신공학과 {

        @DisplayName("전공영역은 설계, 실험, 일반, 선후수 세부요건을 검사할 수 있다.")
        @CsvSource(value = {"005611, 디지털논리회로", "009658, 전기회로실험", "008076, 기초광학및실험", "999999, 전자소자공정실험", "009666, 마이크로컴퓨터실험"})
        @ParameterizedTest
        void 전공영역_검사(String courseCode, String studentLabCourseName) {
            //given
            CompletedCourse completedCourse = CompletedCourse.builder().code(courseCode)
                                                             .name(studentLabCourseName)
                                                             .build();
            Major major = new Major(createLabMajor(),
                                    createGeneralMajor(),
                                    45);

            RequirementResult requirementResult = GivenObjectFixture.createCheckResult();

            //when
            major.checkAllCourses(List.of(completedCourse), requirementResult);

            //then
            assertThat(requirementResult.passResults().get(AbeekType.MAJOR)).isFalse();
        }
    }
}
