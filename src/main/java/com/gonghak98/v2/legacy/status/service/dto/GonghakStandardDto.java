package com.gonghak98.v2.legacy.status.service.dto;


import com.gonghak98.v2.legacy.common.constant.AbeekTypeConst;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GonghakStandardDto {

    private final Map<AbeekTypeConst, Integer> standards;
}
