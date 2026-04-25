package com.gonghak98.v2.report.infrastructure.factory;

import com.gonghak98.v2.report.domain.abeek.AbeekType;
import com.gonghak98.v2.report.domain.abeek.CourseType;
import com.gonghak98.v2.report.domain.abeek.exception.AbeekException;
import com.gonghak98.v2.report.domain.abeek.exception.ExceptionMessage;
import com.gonghak98.v2.report.domain.abeek.gyoyang.Gyoyang;
import com.gonghak98.v2.report.domain.abeek.gyoyang.ProGyoyang;
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
public class GyoyangFactory {

    private final JpaGonghakCourseRepository gonghakCourseRepository;

    public Gyoyang create(DepartmentEntity department) {
        final List<GonghakCourseEntity> gonghakCourses = gonghakCourseRepository.findByDepartmentAndAbeekType(department, AbeekType.GYOYANG);

        if (gonghakCourses.isEmpty()) {
            throw new AbeekException(ExceptionMessage.EMPTY_GONGHAK_COURSE.getMessage());
        }

        List<Course> essentialCourses = gonghakCourses.stream()
                                                      .filter(c -> c.getCourseType() == CourseType.ESSENTIAL)
                                                      .map(GonghakCourseEntity::getCourse)
                                                      .map(CourseEntity::toDomain)
                                                      .toList();
        List<Course> electiveCourses = gonghakCourses.stream()
                                                     .filter(c -> c.getCourseType() == CourseType.ELECTIVE)
                                                     .map(GonghakCourseEntity::getCourse)
                                                     .map(CourseEntity::toDomain)
                                                     .toList();

        int minCredit = 14;
        return new ProGyoyang(essentialCourses, electiveCourses, minCredit);
    }
}
