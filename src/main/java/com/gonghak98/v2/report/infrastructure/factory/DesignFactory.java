package com.gonghak98.v2.report.infrastructure.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gonghak98.v2.report.domain.abeek.AreaType;
import com.gonghak98.v2.report.domain.abeek.design.Design;
import com.gonghak98.v2.report.domain.course.DesignCourse;
import com.gonghak98.v2.report.infrastructure.entity.CourseEntity;
import com.gonghak98.v2.report.infrastructure.entity.DepartmentEntity;
import com.gonghak98.v2.report.infrastructure.entity.GonghakCourseEntity;
import com.gonghak98.v2.report.infrastructure.factory.dto.DesignConfig;
import com.gonghak98.v2.report.infrastructure.jpa.JpaGonghakCourseRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DesignFactory {

    private final JpaGonghakCourseRepository gonghakCourseRepository;
    private final ObjectMapper objectMapper;

    public Design create(final DepartmentEntity department) {
        final DesignConfig designConfig = loadConfig(department.getName());
        final List<GonghakCourseEntity> gonghakCourses = gonghakCourseRepository.findByDepartmentAndCategory(department, AreaType.DESIGN);

        DesignCourse basic = null;
        final List<DesignCourse> elements = new ArrayList<>();
        final List<DesignCourse> comprehensives = new ArrayList<>();

        for (GonghakCourseEntity entity : gonghakCourses) {
            switch (entity.getSubCategory()) {
                case DESIGN_BASIC -> {
                    if (basic != null) {
                        throw new IllegalArgumentException("기초설계 과목이 중복되어 존재합니다.");
                    }
                    basic = toDesignCourse(entity, designConfig.getDesignPointByCourse());
                }
                case DESIGN_ELEMENT -> elements.add(toDesignCourse(entity, designConfig.getDesignPointByCourse()));
                case DESIGN_COMPREHENSIVE -> comprehensives.add(toDesignCourse(entity, designConfig.getDesignPointByCourse()));
                default -> { /* ignore */ }
            }
        }

        if (basic == null) {
            throw new IllegalArgumentException(department.getName() + "에 기초설계 과목이 존재하지 않습니다.");
        }

        return new Design(basic, elements, comprehensives, designConfig.getMinDesignPoint());
    }

    private DesignConfig loadConfig(final String name) {
        String path = "json/design-config/" + name + ".json";
        final ClassPathResource resource = new ClassPathResource(path);
        if (!resource.exists()) {
            throw new IllegalArgumentException(path + "을 찾을 수 없습니다.");
        }
        try {
            final DesignConfig designConfig = objectMapper.readValue(resource.getInputStream(), DesignConfig.class);
            if (designConfig.getDesignPointByCourse() == null) {
                throw new IllegalArgumentException("과목별 설계학점 정보가 존재하지 않습니다.");
            }
            if (designConfig.getMinDesignPoint() == null) {
                throw new IllegalArgumentException("최소 설계학점 정보가 존재하지 않습니다.");
            }
            return designConfig;
        } catch (IOException e) {
            throw new IllegalArgumentException("파일을 읽어오는 중 에러가 발생했습니다.");
        }
    }

    private DesignCourse toDesignCourse(final GonghakCourseEntity entity, final Map<Integer, Integer> designPointByCourse) {
        final CourseEntity courseEntity = entity.getCourse();
        double point = designPointByCourse.getOrDefault(courseEntity.getCourseId(), 0);
        return new DesignCourse(courseEntity.toDomain(), point);
    }
}
