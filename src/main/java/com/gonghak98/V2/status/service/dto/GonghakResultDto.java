package com.gonghak98.V2.status.service.dto;

import com.gonghak98.V2.common.constant.AbeekTypeConst;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GonghakResultDto {

    private final Map<AbeekTypeConst, AbeekDetailsDto> userResult;
}
