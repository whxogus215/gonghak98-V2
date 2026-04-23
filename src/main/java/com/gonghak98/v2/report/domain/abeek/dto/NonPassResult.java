package com.gonghak98.v2.report.domain.abeek.dto;

import com.gonghak98.v2.report.domain.abeek.NonPassMessage;

public record NonPassResult(String courseCode,
                            String courseName,
                            NonPassMessage nonPassMessage) {

}
