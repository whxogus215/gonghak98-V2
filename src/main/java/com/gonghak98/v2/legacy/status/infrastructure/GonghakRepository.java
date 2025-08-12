package com.gonghak98.v2.legacy.status.infrastructure;

import com.gonghak98.v2.legacy.common.constant.CourseCategory;
import com.gonghak98.v2.legacy.common.domain.MajorsDomain;
import com.gonghak98.v2.legacy.status.domain.AbeekDomain;
import com.gonghak98.v2.legacy.status.service.dto.CourseDetailsDto;
import com.gonghak98.v2.legacy.status.service.dto.GonghakStandardDto;
import com.gonghak98.v2.legacy.status.service.dto.IncompletedCoursesDto;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface GonghakRepository {

    AbeekDomain save(AbeekDomain abeekDomain);

    Optional<GonghakStandardDto> findStandard(MajorsDomain majorsDomain);

    List<CourseDetailsDto> findUserCompletedCourses(Long studentId, MajorsDomain majorsDomain);

    List<IncompletedCoursesDto> findUserIncompletedCourses(List<CourseCategory> courseCategories, Long studentId, MajorsDomain majorsDomain);
}
