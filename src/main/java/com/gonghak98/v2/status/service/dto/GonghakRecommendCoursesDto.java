package com.gonghak98.v2.status.service.dto;

import com.gonghak98.v2.common.constant.AbeekTypeConst;
import java.util.List;
import java.util.Map;

public record GonghakRecommendCoursesDto(Map<AbeekTypeConst, List<IncompletedCoursesDto>> recommendCourses) {
}
