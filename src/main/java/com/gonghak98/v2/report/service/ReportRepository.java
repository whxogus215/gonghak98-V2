package com.gonghak98.v2.report.service;

import com.gonghak98.v2.report.controller.dto.ReportResponse;
import com.gonghak98.v2.report.domain.abeek.dto.CheckResult;

public interface ReportRepository {

    ReportResponse save(CheckResult checkResult);
}
