package com.gonghak98.v2.certification.controller;

import java.util.List;
import java.util.Map;

public record CertificationResult(
    Map<AreaType, AreaResult> areaResults,
    List<UnrecognizedCourse> unrecognizedCourses
) {

}
