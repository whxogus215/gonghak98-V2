package com.example.gimmegonghakauth.abeek.service;

import com.example.gimmegonghakauth.status.service.MyAbeekService;
import com.example.gimmegonghakauth.status.service.dto.MyAbeekResponse;
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
