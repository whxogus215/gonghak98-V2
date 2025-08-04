package com.gonghak98.v2.requirement.major;

import static org.assertj.core.api.Assertions.assertThat;

import com.gonghak98.v2.completedcourse.CompletedCourse;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class LabMajorTest {

    private LabMajor labMajor;

    @BeforeEach
    void setUp() {
        Set<String> essentialLabCourses = Set.of("디지털논리회로", "전기회로실험", "기초광학및실험", "전자소자공정실험", "마이크로컴퓨터실험");
        int minCount = 1;
        labMajor = new LabMajor(essentialLabCourses, minCount);
    }

    @ParameterizedTest
    @CsvSource(value = {"디지털논리회로", "전기회로실험", "기초광학및실험", "전자소자공정실험", "마이크로컴퓨터실험"})
    @DisplayName("지정된 실험교과목을 1과목 이상 이수하면, 실험 교과목 이수조건을 만족한다.")
    void 한_개_과목이상_이수(String studentCourseName) {
        //given
        List<CompletedCourse> studentCourses = 랜덤이름을_가진_기이수_과목_특정_개수만큼_생성(5);
        studentCourses.add(CompletedCourse.builder().name(studentCourseName).point(3).build());

        //when
        boolean isCertificated = labMajor.check(studentCourses);

        //then
        assertThat(isCertificated).isTrue();
    }

    private List<CompletedCourse> 랜덤이름을_가진_기이수_과목_특정_개수만큼_생성(int minimumCount) {
        List<CompletedCourse> courses = new ArrayList<>();
        for (int i = 1; i <= minimumCount; i++) {
            courses.add(CompletedCourse.builder().name("랜덤과목-" + i).build());
        }
        return courses;
    }
}
