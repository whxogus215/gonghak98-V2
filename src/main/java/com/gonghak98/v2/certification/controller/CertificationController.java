package com.gonghak98.v2.certification.controller;

import com.gonghak98.v2.certification.service.CertificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CertificationController {

    private final CertificationService certificationService;

    @PostMapping("/api/certification/check")
    public CertificationResult checkCertification(CertificationRequest request) {
        return certificationService.getCertificationResult(request);
    }
}
