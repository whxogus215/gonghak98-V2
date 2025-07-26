package com.gonghak98.v2.certification.service;

import com.gonghak98.v2.status.service.MyAbeekService;
import com.gonghak98.v2.status.service.dto.MyAbeekResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
@RequiredArgsConstructor
public class LegacyAbeekService implements GonghakAbeekService {

    private final MyAbeekService myabeekService;

    @Override
    public MyAbeekResponse getUserResult(Long studentId) {
        return myabeekService.getUserResult(studentId);
    }
}
