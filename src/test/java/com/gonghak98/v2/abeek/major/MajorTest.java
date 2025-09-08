package com.gonghak98.v2.abeek.major;

import static com.gonghak98.v2.abeek.fixture.MajorFactory.createGeneralMajor;
import static com.gonghak98.v2.abeek.fixture.MajorFactory.createLabMajor;
import static org.assertj.core.api.Assertions.assertThat;

import com.gonghak98.v2.report.domain.abeek.AreaType;
import com.gonghak98.v2.report.domain.abeek.dto.CheckResult;
import com.gonghak98.v2.abeek.fixture.GivenObjectFactory;
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
        @CsvSource(value = {"5611, 디지털논리회로", "9658, 전기회로실험", "8076, 기초광학및실험", "-1, 전자소자공정실험", "9666, 마이크로컴퓨터실험"})
        @ParameterizedTest
        void 전공영역_검사(int courseId, String studentLabCourseName) {
            //given
            CompletedCourse completedCourse = CompletedCourse.builder().id(courseId)
                                                             .name(studentLabCourseName)
                                                             .build();
            Major major = new Major(createLabMajor(),
                                    createGeneralMajor());

            CheckResult checkResult = GivenObjectFactory.createCheckResult();

            //when
            major.checkAllCourses(List.of(completedCourse), checkResult);

            //then
            assertThat(checkResult.passResults().get(AreaType.MAJOR)).isFalse();
        }
    }
}
