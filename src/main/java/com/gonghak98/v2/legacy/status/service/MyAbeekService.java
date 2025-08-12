package com.gonghak98.v2.legacy.status.service;

import com.gonghak98.v2.legacy.common.domain.MajorsDomain;
import com.gonghak98.v2.legacy.status.domain.Abeek;
import com.gonghak98.v2.legacy.status.service.dto.CourseDetailsDto;
import com.gonghak98.v2.legacy.status.service.dto.GonghakRecommendCoursesDto;
import com.gonghak98.v2.legacy.status.service.dto.GonghakResultDto;
import com.gonghak98.v2.legacy.status.service.dto.GonghakStandardDto;
import com.gonghak98.v2.legacy.status.service.dto.MyAbeekResponse;
import com.gonghak98.v2.legacy.status.service.recommend.GonghakRecommendService;
import com.gonghak98.v2.legacy.status.service.recommend.RecommendServiceSelectManager;
import com.gonghak98.v2.legacy.user.domain.UserDomain;
import com.gonghak98.v2.legacy.user.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyAbeekService {

    private final UserService userService;
    private final AbeekService abeekService;
    private final GonghakCoursesService gonghakCoursesService;
    private final RecommendServiceSelectManager recommendServiceSelectManager;

    @Transactional(readOnly = true)
    public MyAbeekResponse getUserResult(Long studentId) {
        UserDomain user = userService.getByStudentId(studentId);
        MajorsDomain major = user.getMajorsDomain();

        // 사용자 인증현황 조회
        GonghakStandardDto gonghakStandard = abeekService.findLatestStandardByMajor(major).orElseThrow(IllegalArgumentException::new);
        List<CourseDetailsDto> completedCourse = gonghakCoursesService.findUserCompletedCourses(studentId, major);
        Abeek abeek = new Abeek(gonghakStandard);
        GonghakResultDto gonghakResultDto = abeek.getResult(completedCourse).orElseThrow(IllegalArgumentException::new);

        // 사용자 인증현황에 따른 추천 과목 조회
        GonghakRecommendService gonghakRecommendService = recommendServiceSelectManager.selectRecommendService(major);
        GonghakRecommendCoursesDto recommendCourses = gonghakRecommendService.createRecommendCourses(user, gonghakStandard);

        return new MyAbeekResponse(gonghakResultDto, recommendCourses);
    }
}
