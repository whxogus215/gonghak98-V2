package com.gonghak98.v2.audit.prerequisite;

import static org.assertj.core.api.Assertions.assertThat;

import com.gonghak98.v2.audit.domain.prerequisite.PrerequisiteChecker;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PrerequisiteAuditCheckerTest {
    
    @Nested
    class 성공할때 {
        
        @Test
        @DisplayName("년도가 같을 때")
        void 성공_테스트1() {
            //given
            CompletedCourse before = CompletedCourse.builder().year(2026).semester(1).build();
            CompletedCourse after = CompletedCourse.builder().year(2026).semester(2).build();

            //when
            final boolean isSatisfied = PrerequisiteChecker.isSatisfiedPrerequisite(before, after);

            //then
            assertThat(isSatisfied).isTrue();
        }

        @ParameterizedTest
        @CsvSource({"2025, 1, 2026, 1", "2025, 1, 2026, 2", "2025, 2, 2026, 1"})
        @DisplayName("년도가 다를 때")
        void 성공_테스트2(int beforeYear, int beforeSemester, int afterYear, int afterSemester) {
            //given
            CompletedCourse before = CompletedCourse.builder().year(beforeYear).semester(beforeSemester).build();
            CompletedCourse after = CompletedCourse.builder().year(afterYear).semester(afterSemester).build();

            //when
            final boolean isSatisfied = PrerequisiteChecker.isSatisfiedPrerequisite(before, after);

            //then
            assertThat(isSatisfied).isTrue();
        }
    }
    
    @Nested
    class 실패할때 {
        
        @ParameterizedTest
        @CsvSource({"2026, 1, 2025, 1", "2026, 1, 2025, 2", "2026, 2, 2025, 1"})
        @DisplayName("선수과목의 년도가 더 나중일 때")
        void 실패_테스트1(int beforeYear, int beforeSemester, int afterYear, int afterSemester) {
            //given
            CompletedCourse before = CompletedCourse.builder().year(beforeYear).semester(beforeSemester).build();
            CompletedCourse after = CompletedCourse.builder().year(afterYear).semester(afterSemester).build();

            //when
            final boolean isSatisfied = PrerequisiteChecker.isSatisfiedPrerequisite(before, after);

            //then
            assertThat(isSatisfied).isFalse();
        }

        @Test
        @DisplayName("년도가 같지만, 선수과목의 학기가 더 나중일 때")
        void 실패_테스트2() {
            //given
            CompletedCourse before = CompletedCourse.builder().year(2026).semester(2).build();
            CompletedCourse after = CompletedCourse.builder().year(2026).semester(1).build();

            //when
            final boolean isSatisfied = PrerequisiteChecker.isSatisfiedPrerequisite(before, after);

            //then
            assertThat(isSatisfied).isFalse();
        }

        @Test
        @DisplayName("년도와 학기가 모두 같을 때 (동시 수강)")
        void 실패_테스트3() {
            //given
            CompletedCourse before = CompletedCourse.builder().year(2026).semester(1).build();
            CompletedCourse after = CompletedCourse.builder().year(2026).semester(1).build();

            //when
            final boolean isSatisfied = PrerequisiteChecker.isSatisfiedPrerequisite(before, after);

            //then
            assertThat(isSatisfied).isFalse();
        }
    }
}
