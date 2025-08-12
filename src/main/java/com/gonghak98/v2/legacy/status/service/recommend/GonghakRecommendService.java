package com.gonghak98.v2.legacy.status.service.recommend;

import com.gonghak98.v2.legacy.status.service.dto.GonghakRecommendCoursesDto;
import com.gonghak98.v2.legacy.status.service.dto.GonghakStandardDto;
import com.gonghak98.v2.legacy.user.domain.UserDomain;

public interface GonghakRecommendService {
    GonghakRecommendCoursesDto createRecommendCourses(UserDomain userDomain, GonghakStandardDto standard);
}
