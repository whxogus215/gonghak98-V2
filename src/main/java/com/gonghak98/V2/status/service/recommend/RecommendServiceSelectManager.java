package com.gonghak98.V2.status.service.recommend;

import static com.gonghak98.V2.status.domain.MajorName.ELEC_INFO;

import com.gonghak98.V2.common.domain.MajorsDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecommendServiceSelectManager {

    private final ApplicationContext applicationContext;

    // 학과에 따른 추천 서비스를 설정한다.
    public GonghakRecommendService selectRecommendService(MajorsDomain majorsDomain) {
        if (majorsDomain.getMajor().contains(ELEC_INFO.getName())) {
            return applicationContext.getBean("elecInfoMajorGonghakRecommendService",
                    ElecInfoMajorGonghakRecommendService.class);
        }
        return applicationContext.getBean("computerMajorGonghakRecommendService",
                ComputerMajorGonghakRecommendService.class);
    }
}
