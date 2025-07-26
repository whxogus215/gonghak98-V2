package com.gonghak98.V2.status.service.recommend;

import com.gonghak98.V2.status.service.dto.GonghakRecommendCoursesDto;
import com.gonghak98.V2.status.service.dto.GonghakStandardDto;
import com.gonghak98.V2.user.domain.UserDomain;

public interface GonghakRecommendService {
    GonghakRecommendCoursesDto createRecommendCourses(UserDomain userDomain, GonghakStandardDto standard);
}
