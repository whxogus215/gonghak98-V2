package com.gonghak98.v2.report.infrastructure.factory;

import com.gonghak98.v2.report.domain.abeek.AreaType;
import com.gonghak98.v2.report.domain.abeek.CourseType;
import com.gonghak98.v2.report.domain.abeek.exception.AbeekException;
import com.gonghak98.v2.report.domain.abeek.gyoyang.Gyoyang;
import com.gonghak98.v2.report.domain.abeek.gyoyang.ProGyoyang;
import com.gonghak98.v2.report.domain.course.Course;
import com.gonghak98.v2.report.infrastructure.entity.CourseEntity;
import com.gonghak98.v2.report.infrastructure.entity.DepartmentEntity;
import com.gonghak98.v2.report.infrastructure.entity.GonghakCourseEntity;
import com.gonghak98.v2.report.infrastructure.jpa.JpaDepartmentRepository;
import com.gonghak98.v2.report.infrastructure.jpa.JpaGonghakCourseRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GyoyangFactory {

    private final JpaGonghakCourseRepository gonghakCourseRepository;
    private final JpaDepartmentRepository jpaDepartmentRepository;

    public Gyoyang create(String departmentName) {
        final DepartmentEntity department = jpaDepartmentRepository.findByName(departmentName)
                                                                   .orElseThrow(() -> new AbeekException("학과가 존재하지 않습니다."));
        final List<GonghakCourseEntity> gonghakCourses = gonghakCourseRepository.findByDepartmentAndCategory(department, AreaType.GYOYANG);

        if (gonghakCourses.isEmpty()) {
            throw new AbeekException("공학인증 과목이 존재하지 않습니다.");
        }

        List<Course> essentialCourses = gonghakCourses.stream()
                                                      .filter(c -> c.getSubCategory() == CourseType.ESSENTIAL)
                                                      .map(GonghakCourseEntity::getCourse)
                                                      .map(CourseEntity::toDomain)
                                                      .toList();
        List<Course> electiveCourses = gonghakCourses.stream()
                                                     .filter(c -> c.getSubCategory() == CourseType.ELECTIVE)
                                                     .map(GonghakCourseEntity::getCourse)
                                                     .map(CourseEntity::toDomain)
                                                     .toList();

        int minPoint = 14;
        return new ProGyoyang(essentialCourses, electiveCourses, minPoint);
    }
}
