package com.gonghak98.v2.report.infrastructure.factory;

import com.gonghak98.v2.report.domain.abeek.AreaType;
import com.gonghak98.v2.report.domain.abeek.CourseType;
import com.gonghak98.v2.report.domain.abeek.basic.Basic;
import com.gonghak98.v2.report.domain.abeek.basic.msc.MscBasic;
import com.gonghak98.v2.report.domain.abeek.exception.AbeekException;
import com.gonghak98.v2.report.domain.abeek.exception.ExceptionMessage;
import com.gonghak98.v2.report.domain.course.Course;
import com.gonghak98.v2.report.infrastructure.entity.CourseEntity;
import com.gonghak98.v2.report.infrastructure.entity.DepartmentEntity;
import com.gonghak98.v2.report.infrastructure.entity.GonghakCourseEntity;
import com.gonghak98.v2.report.infrastructure.jpa.JpaGonghakCourseRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BasicFactory {

    private final JpaGonghakCourseRepository gonghakCourseRepository;

    public Basic create(DepartmentEntity department) {
        final AreaType basicType = AreaType.getBasicType(department.getName());
        final List<GonghakCourseEntity> gonghakCourses = gonghakCourseRepository.findByDepartmentAndCategory(department, basicType);

        if (gonghakCourses.isEmpty()) {
            throw new AbeekException(ExceptionMessage.EMPTY_GONGHAK_COURSE.getMessage());
        }

        List<Course> essentialCourses = gonghakCourses.stream()
                                                      .filter(c -> c.getSubCategory() == CourseType.ESSENTIAL)
                                                      .map(GonghakCourseEntity::getCourse)
                                                      .map(CourseEntity::toDomain)
                                                      .toList();

        return new MscBasic(essentialCourses);
    }
}
