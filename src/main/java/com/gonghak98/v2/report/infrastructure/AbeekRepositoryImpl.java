package com.gonghak98.v2.report.infrastructure;

import com.gonghak98.v2.report.domain.abeek.Abeek;
import com.gonghak98.v2.report.domain.abeek.basic.Basic;
import com.gonghak98.v2.report.domain.abeek.design.Design;
import com.gonghak98.v2.report.domain.abeek.exception.AbeekException;
import com.gonghak98.v2.report.domain.abeek.gyoyang.Gyoyang;
import com.gonghak98.v2.report.domain.abeek.major.Major;
import com.gonghak98.v2.report.domain.abeek.prerequisite.Prerequisite;
import com.gonghak98.v2.report.infrastructure.entity.DepartmentEntity;
import com.gonghak98.v2.report.infrastructure.factory.BasicFactory;
import com.gonghak98.v2.report.infrastructure.factory.DesignFactory;
import com.gonghak98.v2.report.infrastructure.factory.GyoyangFactory;
import com.gonghak98.v2.report.infrastructure.factory.MajorFactory;
import com.gonghak98.v2.report.infrastructure.factory.PrerequisiteFactory;
import com.gonghak98.v2.report.infrastructure.jpa.JpaDepartmentRepository;
import com.gonghak98.v2.report.service.AbeekRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AbeekRepositoryImpl implements AbeekRepository {

    private final JpaDepartmentRepository jpaDepartmentRepository;
    private final GyoyangFactory gyoyangFactory;
    private final BasicFactory basicFactory;
    private final MajorFactory majorFactory;
    private final DesignFactory designFactory;
    private final PrerequisiteFactory prerequisiteFactory;

    @Override
    public Abeek findAbeek(String departmentName) {
        final DepartmentEntity department = jpaDepartmentRepository.findByName(departmentName)
                                                                   .orElseThrow(() -> new AbeekException("학과가 존재하지 않습니다."));

        Gyoyang gyoyang = gyoyangFactory.create(department);
        Basic basic = basicFactory.create(department);
        Major major = majorFactory.create(department);
        Design design = designFactory.create(department);
        Prerequisite prerequisite = prerequisiteFactory.create(department);

        return new Abeek(gyoyang, basic, major, design, prerequisite);
    }
}
