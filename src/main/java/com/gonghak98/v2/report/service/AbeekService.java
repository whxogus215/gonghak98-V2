package com.gonghak98.v2.report.service;

import com.gonghak98.v2.report.domain.abeek.Abeek;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AbeekService {

    private final AbeekRepository abeekRepository;

    public Abeek getAbeek(String departmentName) {
        return abeekRepository.findAbeek(departmentName);
    }
}
