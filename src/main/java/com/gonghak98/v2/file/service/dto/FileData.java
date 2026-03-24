package com.gonghak98.v2.file.service.dto;

public record FileData(Long courseId,
                       String courseName,
                       int year,
                       int semester,
                       double point) {

}
