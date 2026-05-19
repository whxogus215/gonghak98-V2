package com.gonghak98.v2.audit.domain.abeek;

import static com.gonghak98.v2.audit.fixture.GyoyangFixture.createProGyoyang;
import static org.assertj.core.api.Assertions.assertThat;

import com.gonghak98.v2.audit.domain.constant.AbeekType;
import com.gonghak98.v2.audit.domain.dto.AbeekAreaAuditResult;
import com.gonghak98.v2.core.domain.course.CompletedCourse;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ProGyoyangTest {

    @Nested
    @DisplayName("2021년 이전의 전문교양 영역 조건 검사")
    class Until2021 {

        @DisplayName("필수 과목을 모두 이수하면, 전문교양 영역을 만족한다.")
        @Test
        void 전문교양_영역_검사() {
            //given
            List<CompletedCourse> studentCourses = List.of(
                CompletedCourse.builder().code("010352").name("English Listening Practice 1").build(),
                CompletedCourse.builder().code("010354").name("English Reading Practice 1").build(),
                CompletedCourse.builder().code("008364").name("세종사회봉사1").build(),
                CompletedCourse.builder().code("009489").name("세계사:인간과문명").build(),
                CompletedCourse.builder().code("009067").name("문제해결을위한글쓰기와발표").build(),
                CompletedCourse.builder().code("009068").name("서양철학:쟁점과토론").build()
            );
            ProGyoyang gyoyang = createProGyoyang();

            //when
            AbeekAreaAuditResult auditResult = gyoyang.audit(studentCourses);

            //then
            assertThat(auditResult.passResults().get(AbeekType.GYOYANG)).isTrue();
        }

        @DisplayName("필수 과목을 모두 이수하고, 선택 과목을 추가로 이수하더라도 전문교양 영역을 만족한다.")
        @Test
        void 전문교양_영역_검사2() {
            //given
            List<CompletedCourse> studentCourses = List.of(
                CompletedCourse.builder().code("010352").name("English Listening Practice 1").build(),
                CompletedCourse.builder().code("010354").name("English Reading Practice 1").build(),
                CompletedCourse.builder().code("008364").name("세종사회봉사1").build(),
                CompletedCourse.builder().code("009489").name("세계사:인간과문명").build(),
                CompletedCourse.builder().code("009067").name("문제해결을위한글쓰기와발표").build(),
                CompletedCourse.builder().code("009068").name("서양철학:쟁점과토론").build(),

                CompletedCourse.builder().code("011313").name("경제학").build()
            );
            ProGyoyang gyoyang = createProGyoyang();

            //when
            AbeekAreaAuditResult auditResult = gyoyang.audit(studentCourses);

            //then
            assertThat(auditResult.passResults().get(AbeekType.GYOYANG)).isTrue();
        }

        @DisplayName("필수 과목을 모두 이수하지 못하면, 전문교양 영역을 만족하지 못한다.")
        @Test
        void 전문교양_영역_실패() {
            //given
            List<CompletedCourse> studentCourses = List.of(
                CompletedCourse.builder().code("010352").name("English Listening Practice 1").build(),
                CompletedCourse.builder().code("010354").name("English Reading Practice 1").build(),
                CompletedCourse.builder().code("008364").name("세종사회봉사1").build(),
                CompletedCourse.builder().code("009489").name("세계사:인간과문명").build()
            );
            ProGyoyang gyoyang = createProGyoyang();

            //when
            AbeekAreaAuditResult auditResult = gyoyang.audit(studentCourses);

            //then
            assertThat(auditResult.passResults().get(AbeekType.GYOYANG)).isFalse();
        }
    }

    @Nested
    @DisplayName("2022년 이후의 전문교양 영역 조건 검사")
    class From2022 {

        @DisplayName("필수 과목을 모두 이수하고, 선택 교과목 중 2과목 이상을 포함해 14학점 이상 이수하면, 전문교양 영역을 만족한다.")
        @MethodSource("provideAllEssentialAndSufficientElectiveCourses")
        @ParameterizedTest
        void 전문교양_영역_검사(List<CompletedCourse> studentCourses) {
            //given
            ProGyoyang gyoyang = createProGyoyang();

            //when
            AbeekAreaAuditResult auditResult = gyoyang.audit(studentCourses);

            //then
            assertThat(auditResult.passResults().get(AbeekType.GYOYANG)).isTrue();
        }

        @DisplayName("필수 과목을 모두 이수하지 않으면, 선택 교과목 중 2과목 이상을 포함해도, 전문교양 영역을 만족하지 못한다.")
        @MethodSource("provideInsufficientEssentialAndSufficientElectiveCourses")
        @ParameterizedTest
        void 전문교양_영역_검사_실패(List<CompletedCourse> studentCourses) {
            //given
            ProGyoyang gyoyang = createProGyoyang();

            //when
            AbeekAreaAuditResult auditResult = gyoyang.audit(studentCourses);

            //then
            assertThat(auditResult.passResults().get(AbeekType.GYOYANG)).isFalse();
        }

        @DisplayName("필수 과목을 모두 이수해도, 선택 교과목 중 2과목 미만으로 이수하면, 전문교양 영역을 만족하지 못한다.")
        @MethodSource("provideAllEssentialAndInsufficientElectiveCourses")
        @ParameterizedTest
        void 전문교양_영역_검사_실패2(List<CompletedCourse> studentCourses) {
            //given
            ProGyoyang gyoyang = createProGyoyang();

            //when
            AbeekAreaAuditResult auditResult = gyoyang.audit(studentCourses);

            //then
            assertThat(auditResult.passResults().get(AbeekType.GYOYANG)).isFalse();
        }

        private static List<CompletedCourse> createEssentialCourses() {
            return List.of(
                CompletedCourse.builder().code("009067").name("문제해결을위한글쓰기와발표").build(),
                CompletedCourse.builder().code("009068").name("서양철학:쟁점과토론").build(),
                CompletedCourse.builder().code("011304").name("대학영어").build()
            );
        }

        private static List<CompletedCourse> createAllElectiveCourses() {
            return List.of(
                CompletedCourse.builder().code("011307").name("세계사").build(),
                CompletedCourse.builder().code("011305").name("동서양의사상과윤리").build(),
                CompletedCourse.builder().code("011313").name("경제학").build(),
                CompletedCourse.builder().code("011312").name("경영학").build(),
                CompletedCourse.builder().code("011317").name("컴퓨터게임과메타버스").build(),
                CompletedCourse.builder().code("011316").name("융합예술의이해").build()
            );
        }

        private static Stream<Arguments> provideAllEssentialAndSufficientElectiveCourses() {
            List<CompletedCourse> essentialCourses = createEssentialCourses();

            List<CompletedCourse> electiveCourses = createAllElectiveCourses();
            List<CompletedCourse> electiveCourses1 = electiveCourses.subList(0, 2);
            List<CompletedCourse> electiveCourses2 = electiveCourses.subList(2, 4);
            List<CompletedCourse> electiveCourses3 = electiveCourses.subList(4, 6);

            return Stream.of(
                Arguments.of(Stream.concat(essentialCourses.stream(), electiveCourses1.stream()).toList()),
                Arguments.of(Stream.concat(essentialCourses.stream(), electiveCourses2.stream()).toList()),
                Arguments.of(Stream.concat(essentialCourses.stream(), electiveCourses3.stream()).toList())
            );
        }

        private static Stream<Arguments> provideInsufficientEssentialAndSufficientElectiveCourses() {
            List<CompletedCourse> essentialCourses = createEssentialCourses().subList(0, 2);

            List<CompletedCourse> electiveCourses = createAllElectiveCourses();
            List<CompletedCourse> electiveCourses1 = electiveCourses.subList(0, 2);
            List<CompletedCourse> electiveCourses2 = electiveCourses.subList(2, 4);
            List<CompletedCourse> electiveCourses3 = electiveCourses.subList(4, 6);

            return Stream.of(
                Arguments.of(Stream.concat(essentialCourses.stream(), electiveCourses1.stream()).toList()),
                Arguments.of(Stream.concat(essentialCourses.stream(), electiveCourses2.stream()).toList()),
                Arguments.of(Stream.concat(essentialCourses.stream(), electiveCourses3.stream()).toList())
            );
        }

        private static Stream<Arguments> provideAllEssentialAndInsufficientElectiveCourses() {
            List<CompletedCourse> essentialCourses = createEssentialCourses();

            List<CompletedCourse> electiveCourses = createAllElectiveCourses();
            List<CompletedCourse> electiveCourses1 = electiveCourses.subList(0, 1);
            List<CompletedCourse> electiveCourses2 = electiveCourses.subList(1, 2);
            List<CompletedCourse> electiveCourses3 = electiveCourses.subList(2, 3);
            List<CompletedCourse> electiveCourses4 = electiveCourses.subList(3, 4);
            List<CompletedCourse> electiveCourses5 = electiveCourses.subList(4, 5);
            List<CompletedCourse> electiveCourses6 = electiveCourses.subList(5, 6);

            return Stream.of(
                Arguments.of(Stream.concat(essentialCourses.stream(), electiveCourses1.stream()).toList()),
                Arguments.of(Stream.concat(essentialCourses.stream(), electiveCourses2.stream()).toList()),
                Arguments.of(Stream.concat(essentialCourses.stream(), electiveCourses3.stream()).toList()),
                Arguments.of(Stream.concat(essentialCourses.stream(), electiveCourses4.stream()).toList()),
                Arguments.of(Stream.concat(essentialCourses.stream(), electiveCourses5.stream()).toList()),
                Arguments.of(Stream.concat(essentialCourses.stream(), electiveCourses6.stream()).toList())
            );
        }
    }
}
