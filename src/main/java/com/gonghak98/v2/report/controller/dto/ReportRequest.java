package com.gonghak98.v2.report.controller.dto;

import org.springframework.web.multipart.MultipartFile;

public record ReportRequest(String departmentName,
                            Short entranceYear,
                            MultipartFile file) {

}
