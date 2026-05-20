package com.gonghak98.v2.audit.domain.abeek;

import static com.gonghak98.v2.audit.fixture.BasicFixture.createMscBasic;
import static org.assertj.core.api.Assertions.assertThat;

import com.gonghak98.v2.audit.domain.constant.AbeekType;
import com.gonghak98.v2.audit.domain.dto.AbeekAreaAuditResult;
import com.gonghak98.v2.audit.domain.dto.AuditCompletedCourse;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class BasicTest {

    @Nested
    class 전자정보통신공학과 {

        @DisplayName("지정된 MSC 교과목을 모두 이수하면, MSC 영역을 만족한다.")
        @MethodSource("provideAllEssentialMscCourses")
        @ParameterizedTest
        void MSC_영역_검사(List<AuditCompletedCourse> studentCourses) {
            //given
            Basic basic = createMscBasic();

            //when
            AbeekAreaAuditResult abeekAreaAuditResult = basic.audit(studentCourses);

            //then
            assertThat(abeekAreaAuditResult.passResults().get(AbeekType.MSC)).isTrue();
        }

        @DisplayName("지정된 MSC 교과목을 모두 이수하지 못하면, MSC 영역을 만족하지 못한다.")
        @MethodSource("provideNotAllEssentialMscCourses")
        @ParameterizedTest
        void MSC_영역_검사2(List<AuditCompletedCourse> studentCourses) {
            //given
            Basic basic = createMscBasic();

            //when
            AbeekAreaAuditResult abeekAreaAuditResult = basic.audit(studentCourses);

            //then
            assertThat(abeekAreaAuditResult.passResults().get(AbeekType.MSC)).isFalse();
        }

        private static Stream<Arguments> provideAllEssentialMscCourses() {
            List<AuditCompletedCourse> essentialCourses = List.of(
                AuditCompletedCourse.builder().code("011300").name("고급프로그래밍활용").build(),
                AuditCompletedCourse.builder().code("007330").name("확률및통계").build(),
                AuditCompletedCourse.builder().code("009912").name("C프로그래밍및실습").build(),
                AuditCompletedCourse.builder().code("001357").name("미적분학1").build(),
                AuditCompletedCourse.builder().code("000304").name("공업수학1").build(),
                AuditCompletedCourse.builder().code("009913").name("고급C프로그래밍및실습").build(),
                AuditCompletedCourse.builder().code("001725").name("선형대수").build(),
                AuditCompletedCourse.builder().code("011320").name("인공지능과빅데이터").build(),
                AuditCompletedCourse.builder().code("011678").name("기초전자물리").build()
            );

            List<AuditCompletedCourse> otherCourses = List.of(
                AuditCompletedCourse.builder().code("999999").name("테스트과목").build()
            );

            return Stream.of(
                Arguments.of(Stream.concat(essentialCourses.stream(), otherCourses.stream()).toList())
            );
        }

        private static Stream<Arguments> provideNotAllEssentialMscCourses() {
            List<AuditCompletedCourse> essentialCourses = List.of(
                AuditCompletedCourse.builder().code("011300").name("고급프로그래밍활용").build(),
                AuditCompletedCourse.builder().code("007330").name("확률및통계").build(),
                AuditCompletedCourse.builder().code("009912").name("C프로그래밍및실습").build(),
                AuditCompletedCourse.builder().code("001357").name("미적분학1").build(),
                AuditCompletedCourse.builder().code("000304").name("공업수학1").build()
            );

            List<AuditCompletedCourse> otherCourses = List.of(
                AuditCompletedCourse.builder().code("999999").name("테스트과목").build()
            );

            return Stream.of(
                Arguments.of(Stream.concat(essentialCourses.stream(), otherCourses.stream()).toList())
            );
        }
    }
}
