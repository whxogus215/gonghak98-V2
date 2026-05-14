package com.gonghak98.v2.audit.infrastructure;

import com.gonghak98.v2.audit.application.QualificationAuditRepository;
import com.gonghak98.v2.audit.domain.QualificationAudit;
import com.gonghak98.v2.audit.domain.abeek.AbeekAreaAudit;
import com.gonghak98.v2.audit.domain.abeek.Basic;
import com.gonghak98.v2.audit.domain.abeek.Design;
import com.gonghak98.v2.audit.domain.abeek.Major;
import com.gonghak98.v2.audit.domain.abeek.ProGyoyang;
import com.gonghak98.v2.audit.domain.constant.AbeekType;
import com.gonghak98.v2.audit.domain.exception.AbeekException;
import com.gonghak98.v2.audit.infrastructure.dto.RequirementDetail;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import com.gonghak98.v2.report.infrastructure.entity.DepartmentEntity;
import com.gonghak98.v2.report.infrastructure.entity.GonghakCourseEntity;
import com.gonghak98.v2.report.infrastructure.jpa.JpaDepartmentRepository;
import com.gonghak98.v2.report.infrastructure.jpa.JpaGonghakCourseRepository;
import com.gonghak98.v2.report.infrastructure.jpa.JpaGonghakRequirementRepository;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QualificationAuditRepositoryImpl implements QualificationAuditRepository {

    private final JpaDepartmentRepository jpaDepartmentRepository;
    private final JpaGonghakRequirementRepository jpaGonghakRequirementRepository;
    private final JpaGonghakCourseRepository jpaGonghakCourseRepository;

    private final ProGyoyangRepository gyoyangFactory;
    private final BasicRepository basicRepository;
    private final MajorRepository majorRepository;
    private final DesignRepository designRepository;
    private final PrerequisiteRepository prerequisiteRepository;

    @Override
    public QualificationAudit findAbeek(String departmentName, Short entranceYear) {
        final DepartmentEntity findDepartment = jpaDepartmentRepository.findByName(departmentName)
                                                                       .orElseThrow(() -> new AbeekException("학과가 존재하지 않습니다."));
        final RequirementDetail findRequirementDetail = jpaGonghakRequirementRepository.findByDepartmentAndEntranceYear(findDepartment, entranceYear)
                                                                                       .orElseThrow(
                                                                                           () -> new IllegalArgumentException("공학인증 요건 정보가 존재하지 않습니다."))
                                                                                       .getDetail();

        ProGyoyang gyoyang = gyoyangFactory.create(findDepartment);
        Basic basic = basicRepository.create(findDepartment, findRequirementDetail.getBasicRequirement());
        Major major = majorRepository.create(findRequirementDetail.getMajorRequirement());
        Design design = designRepository.create(findDepartment, findRequirementDetail.getDesignRequirement().getMinCredit());

        return new QualificationAudit(new AbeekAreaAudit(List.of(gyoyang, basic, major, design)),
                                      prerequisiteRepository.create(findDepartment, findRequirementDetail.getPrerequisiteRequirement()));
    }

    @Override
    public Map<String, AbeekType> findAbeekTypeOfCompletedCourse(List<CompletedCourse> completedCourses, String departmentName) {
        if (completedCourses == null || completedCourses.isEmpty()) {
            return Collections.emptyMap();
        }
        List<String> courseCodes = completedCourses.stream()
                                                   .map(CompletedCourse::getCode)
                                                   .toList();

        List<GonghakCourseEntity> findCompletedGonghakCourses = jpaGonghakCourseRepository.findAllByDepartmentNameAndCourseCodeIn(departmentName, courseCodes);
        return findCompletedGonghakCourses.stream()
                                          .collect(Collectors.toMap(
                                              gc -> gc.getCourse().getCode(),
                                              GonghakCourseEntity::getAbeekType
                                          ));
    }
}
