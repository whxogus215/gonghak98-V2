package com.gonghak98.v2.audit.infrastructure;

import com.gonghak98.v2.audit.domain.abeek.Design;
import com.gonghak98.v2.audit.domain.constant.AbeekType;
import com.gonghak98.v2.core.domain.course.DesignCourse;
import com.gonghak98.v2.core.infrastructure.entity.DepartmentEntity;
import com.gonghak98.v2.report.infrastructure.entity.GonghakCourseEntity;
import com.gonghak98.v2.report.infrastructure.jpa.JpaGonghakCourseRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DesignRepository {

    private final JpaGonghakCourseRepository gonghakCourseRepository;

    public Design create(final DepartmentEntity department, double minDesignCredit) {
        final List<GonghakCourseEntity> findGonghakDesignCourses = gonghakCourseRepository.findByDepartmentAndAbeekType(department, AbeekType.DESIGN);

        DesignCourse basic = null;
        final List<DesignCourse> elements = new ArrayList<>();
        final List<DesignCourse> comprehensives = new ArrayList<>();

        for (GonghakCourseEntity gonghakCourse : findGonghakDesignCourses) {
            switch (gonghakCourse.getCourseType()) {
                case DESIGN_BASIC -> {
                    if (basic != null) {
                        throw new IllegalArgumentException("기초설계 과목이 중복되어 존재합니다.");
                    }
                    basic = new DesignCourse(gonghakCourse.getCourse().toDomain(), gonghakCourse.getDesignCredit());
                }
                case DESIGN_ELEMENT -> elements.add(new DesignCourse(gonghakCourse.getCourse().toDomain(), gonghakCourse.getDesignCredit()));
                case DESIGN_COMPREHENSIVE -> comprehensives.add(new DesignCourse(gonghakCourse.getCourse().toDomain(), gonghakCourse.getDesignCredit()));
                default -> { /* ignore */ }
            }
        }

        if (basic == null) {
            throw new IllegalArgumentException(department.getName() + "에 기초설계 과목이 존재하지 않습니다.");
        }

        return new Design(basic, elements, comprehensives, minDesignCredit);
    }
}
