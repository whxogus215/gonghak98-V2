package com.gonghak98.v2.certification.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.gonghak98.v2.certification.controller.AreaResult;
import com.gonghak98.v2.certification.controller.AreaType;
import com.gonghak98.v2.certification.controller.CertificationRequest;
import com.gonghak98.v2.certification.controller.CertificationResponse;
import com.gonghak98.v2.certification.controller.MajorAreaDetails;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class CertificationServiceTest {

    @Autowired
    private CertificationService certificationService;

    @Test
    @DisplayName("실험 교과목이 담겨있을 때, 전공 영역과 실험 교과목 세부 조건의 정보를 전달할 수 있다.")
    void testLabCourseCertification() {
        // given
        CertificationRequest request = new CertificationRequest("전자정보통신공학과");

        // when
        CertificationResponse result = certificationService.getCertificationResult(request);

        // then
        final AreaResult labCourseResult = result.areaResults().get(AreaType.MAJOR);
        assertThat(labCourseResult.userPoint()).isEqualTo(3);
        assertThat(labCourseResult.minPoint()).isEqualTo(45);
        assertThat(labCourseResult.isPassed()).isFalse();

        MajorAreaDetails majorAreaDetails = (MajorAreaDetails) labCourseResult.details();
        assertThat(majorAreaDetails.lab().isPassed()).isTrue();
        assertThat(majorAreaDetails.lab().courseNames()).contains("디지털논리회로", "전기회로실험", "기초광학및실험", "전자소자공정실험", "마이크로컴퓨터실험");
        assertThat(majorAreaDetails.lab().description()).isEqualTo("실험교과목을 1과목 이상 이수");
    }
}
