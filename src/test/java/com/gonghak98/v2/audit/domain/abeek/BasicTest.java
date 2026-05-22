package com.gonghak98.v2.audit.domain.abeek;

import static com.gonghak98.v2.audit.fixture.BasicFixture.소프트웨어학과_BASIC_생성;
import static com.gonghak98.v2.audit.fixture.BasicFixture.항공우주공학과_BASIC_생성;
import static com.gonghak98.v2.audit.fixture.BasicFixture.전자정보통신공학과_BASIC_생성;
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
            Basic basic = 전자정보통신공학과_BASIC_생성();

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
            Basic basic = 전자정보통신공학과_BASIC_생성();

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

    @Nested
    class 항공우주공학과 {

        @DisplayName("지정된 MSC 인증필수 교과목을 모두 이수하고 인증선택 교과목을 1개 이상 이수하면, MSC 영역을 만족한다.")
        @MethodSource("provideAllEssentialMscCourses")
        @ParameterizedTest
        void MSC_영역_검사(List<AuditCompletedCourse> studentCourses) {
            //given
            Basic basic = 항공우주공학과_BASIC_생성();

            //when
            AbeekAreaAuditResult abeekAreaAuditResult = basic.audit(studentCourses);

            //then
            assertThat(abeekAreaAuditResult.passResults().get(AbeekType.MSC)).isTrue();
        }

        @DisplayName("지정된 MSC 인증필수 또는 인증선택 조건을 만족하지 못하면, MSC 영역을 만족하지 못한다.")
        @MethodSource("provideNotAllEssentialMscCourses")
        @ParameterizedTest
        void MSC_영역_검사2(List<AuditCompletedCourse> studentCourses) {
            //given
            Basic basic = 항공우주공학과_BASIC_생성();

            //when
            AbeekAreaAuditResult abeekAreaAuditResult = basic.audit(studentCourses);

            //then
            assertThat(abeekAreaAuditResult.passResults().get(AbeekType.MSC)).isFalse();
        }

        private static Stream<Arguments> provideAllEssentialMscCourses() {
            List<AuditCompletedCourse> essentialCourses = List.of(
                AuditCompletedCourse.builder().code("001357").name("미적분학1").credit(3.0).build(),
                AuditCompletedCourse.builder().code("001362").name("미적분학2").credit(3.0).build(),
                AuditCompletedCourse.builder().code("000304").name("공업수학1").credit(3.0).build(),
                AuditCompletedCourse.builder().code("000307").name("공업수학2").credit(3.0).build(),
                AuditCompletedCourse.builder().code("002638").name("일반물리학1").credit(3.0).build(),
                AuditCompletedCourse.builder().code("002705").name("일반화학1").credit(3.0).build(),
                AuditCompletedCourse.builder().code("011298").name("SW기초코딩").credit(3.0).build(),
                AuditCompletedCourse.builder().code("011300").name("고급프로그래밍활용").credit(3.0).build()
            );

            List<AuditCompletedCourse> electiveCourses = List.of(
                AuditCompletedCourse.builder().code("001727").name("선형대수학").credit(3.0).build(),
                AuditCompletedCourse.builder().code("004102").name("수치해석").credit(3.0).build()
            );

            List<AuditCompletedCourse> otherCourses = List.of(
                AuditCompletedCourse.builder().code("999999").name("테스트과목").build()
            );

            return Stream.of(
                Arguments.of(Stream.concat(Stream.concat(essentialCourses.stream(), Stream.of(electiveCourses.get(0))), otherCourses.stream()).toList()),
                Arguments.of(Stream.concat(Stream.concat(essentialCourses.stream(), Stream.of(electiveCourses.get(1))), otherCourses.stream()).toList()),
                Arguments.of(Stream.concat(Stream.concat(essentialCourses.stream(), electiveCourses.stream()), otherCourses.stream()).toList())
            );
        }

        private static Stream<Arguments> provideNotAllEssentialMscCourses() {
            List<AuditCompletedCourse> essentialCourses = List.of(
                AuditCompletedCourse.builder().code("001357").name("미적분학1").credit(3.0).build(),
                AuditCompletedCourse.builder().code("001362").name("미적분학2").credit(3.0).build(),
                AuditCompletedCourse.builder().code("000304").name("공업수학1").credit(3.0).build(),
                AuditCompletedCourse.builder().code("000307").name("공업수학2").credit(3.0).build(),
                AuditCompletedCourse.builder().code("002638").name("일반물리학1").credit(3.0).build()
            );

            List<AuditCompletedCourse> allEssentialCourses = List.of(
                AuditCompletedCourse.builder().code("001357").name("미적분학1").credit(3.0).build(),
                AuditCompletedCourse.builder().code("001362").name("미적분학2").credit(3.0).build(),
                AuditCompletedCourse.builder().code("000304").name("공업수학1").credit(3.0).build(),
                AuditCompletedCourse.builder().code("000307").name("공업수학2").credit(3.0).build(),
                AuditCompletedCourse.builder().code("002638").name("일반물리학1").credit(3.0).build(),
                AuditCompletedCourse.builder().code("002705").name("일반화학1").credit(3.0).build(),
                AuditCompletedCourse.builder().code("011298").name("SW기초코딩").credit(3.0).build(),
                AuditCompletedCourse.builder().code("011300").name("고급프로그래밍활용").credit(3.0).build()
            );

            List<AuditCompletedCourse> electiveCourses = List.of(
                AuditCompletedCourse.builder().code("001727").name("선형대수학").credit(3.0).build()
            );

            List<AuditCompletedCourse> otherCourses = List.of(
                AuditCompletedCourse.builder().code("999999").name("테스트과목").build()
            );

            return Stream.of(
                Arguments.of(Stream.concat(Stream.concat(essentialCourses.stream(), electiveCourses.stream()), otherCourses.stream()).toList()),
                Arguments.of(Stream.concat(allEssentialCourses.stream(), otherCourses.stream()).toList())
            );
        }
    }

    @Nested
    class 소프트웨어학과 {

        @DisplayName("지정된 BSM 교과목을 모두 이수하면, BSM 영역을 만족한다.")
        @MethodSource("provideAllEssentialBsmCourses")
        @ParameterizedTest
        void BSM_영역_검사(List<AuditCompletedCourse> studentCourses) {
            //given
            Basic basic = 소프트웨어학과_BASIC_생성();

            //when
            AbeekAreaAuditResult abeekAreaAuditResult = basic.audit(studentCourses);

            //then
            assertThat(abeekAreaAuditResult.passResults().get(AbeekType.BSM)).isTrue();
        }

        @DisplayName("지정된 BSM 교과목을 모두 이수하지 못하면, BSM 영역을 만족하지 못한다.")
        @MethodSource("provideNotAllEssentialBsmCourses")
        @ParameterizedTest
        void BSM_영역_검사2(List<AuditCompletedCourse> studentCourses) {
            //given
            Basic basic = 소프트웨어학과_BASIC_생성();

            //when
            AbeekAreaAuditResult abeekAreaAuditResult = basic.audit(studentCourses);

            //then
            assertThat(abeekAreaAuditResult.passResults().get(AbeekType.BSM)).isFalse();
        }

        private static Stream<Arguments> provideAllEssentialBsmCourses() {
            List<AuditCompletedCourse> essentialCourses = List.of(
                AuditCompletedCourse.builder().code("001357").name("미적분학1").build(),
                AuditCompletedCourse.builder().code("007330").name("확률및통계").build(),
                AuditCompletedCourse.builder().code("000304").name("공업수학1").build(),
                AuditCompletedCourse.builder().code("001725").name("선형대수").build(),
                AuditCompletedCourse.builder().code("010206").name("일반물리및시뮬레이션").build(),
                AuditCompletedCourse.builder().code("009955").name("이산수학및프로그래밍").build()
            );

            List<AuditCompletedCourse> otherCourses = List.of(
                AuditCompletedCourse.builder().code("999999").name("테스트과목").build()
            );

            return Stream.of(
                Arguments.of(Stream.concat(essentialCourses.stream(), otherCourses.stream()).toList())
            );
        }

        private static Stream<Arguments> provideNotAllEssentialBsmCourses() {
            List<AuditCompletedCourse> essentialCourses = List.of(
                AuditCompletedCourse.builder().code("001357").name("미적분학1").build(),
                AuditCompletedCourse.builder().code("007330").name("확률및통계").build(),
                AuditCompletedCourse.builder().code("000304").name("공업수학1").build(),
                AuditCompletedCourse.builder().code("001725").name("선형대수").build()
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
