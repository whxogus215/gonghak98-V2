package com.gonghak98.v2.report.service;

import com.gonghak98.v2.report.domain.abeek.Abeek;
import com.gonghak98.v2.report.domain.abeek.AbeekType;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.List;
import java.util.Map;

public interface AbeekRepository {

    Abeek findAbeek(String departmentName, Short entranceYear);

    Map<String, AbeekType> findAbeekTypeOfCompletedCourse(List<CompletedCourse> completedCourses, String departmentName);
}
