package com.gonghak98.v2.certification.service;

import com.gonghak98.v2.certification.controller.CertificationRequest;
import com.gonghak98.v2.certification.controller.CertificationResponse;
import com.gonghak98.v2.certification.repository.MajorEntity;
import com.gonghak98.v2.certification.repository.MajorRepository;
import com.gonghak98.v2.certification.repository.RuleEntity;
import com.gonghak98.v2.certification.repository.RuleRepository;
import com.gonghak98.v2.certification.repository.RuleType;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CertificationService {

    private final RuleRepository ruleRepository;
    private final MajorRepository majorRepository;

    private final MajorAndDesignValidator majorAndDesignValidator;

    public CertificationResponse getCertificationResult(CertificationRequest request) {

        final MajorEntity findMajor = majorRepository.findByName(request.majorName());

        System.out.println(findMajor.getId() + " : " + findMajor.getName());

        // 학과 ID와 최신년도 기준으로 List<RuleEntity> 가져오기
        final List<RuleEntity> rules = ruleRepository.findByMajorIdAndYear(findMajor.getId(), CertificationConstant.LATEST.getYear());

        final Map<RuleType, RuleEntity> rulesByType = rules.stream().collect(Collectors.toMap(RuleEntity::getType, r -> r));

        return majorAndDesignValidator.validate(request, rulesByType.get(RuleType.LAB));
    }
}
