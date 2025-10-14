package com.gonghak98.v2.report.infrastructure.factory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gonghak98.v2.report.domain.abeek.major.GeneralMajor;
import com.gonghak98.v2.report.domain.abeek.major.LabMajor;
import com.gonghak98.v2.report.domain.abeek.major.Major;
import com.gonghak98.v2.report.infrastructure.entity.DepartmentEntity;
import java.io.File;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MajorFactory {

    private final ObjectMapper objectMapper;

    public Major create(DepartmentEntity department) {
        LabMajor labMajor;
        GeneralMajor generalMajor;
        String departmentName = department.getName();

        try {
            JsonNode majorConfig = objectMapper.readTree(new File("src/main/resources/json/major-config/" + departmentName + ".json"))
                                               .get("components");
            labMajor = objectMapper.treeToValue(majorConfig.get("labMajor"), LabMajor.class);
            generalMajor = objectMapper.treeToValue(majorConfig.get("generalMajor"), GeneralMajor.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("파일을 읽어오는 중 에러가 발생했습니다.");
        }
        return new Major(labMajor, generalMajor);
    }
}
