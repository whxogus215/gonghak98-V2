package com.gonghak98.v2.report.service;

import com.gonghak98.v2.report.domain.abeek.Abeek;

public interface AbeekRepository {

    Abeek findAbeek(String departmentName);
}
