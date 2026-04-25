package com.gonghak98.v2.report.service;

import com.gonghak98.v2.report.domain.abeek.Abeek;
import com.gonghak98.v2.report.domain.abeek.AbeekType;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AbeekService {

    private final AbeekRepository abeekRepository;

    public Abeek getAbeek(String departmentName, Short entranceYear) {
        return abeekRepository.findAbeek(departmentName, entranceYear);
    }

    public void addAbeekTypeToCompletedCourse(List<CompletedCourse> completedCourses, String departmentName) {
        Map<String, AbeekType> abeekTypeOfCompletedCourses = abeekRepository.findAbeekTypeOfCompletedCourse(completedCourses, departmentName);

        for (CompletedCourse course : completedCourses) {
            AbeekType mappedType = abeekTypeOfCompletedCourses.getOrDefault(course.getCode(), AbeekType.NONE);
            course.setAbeekType(mappedType);
        }
    }
}
