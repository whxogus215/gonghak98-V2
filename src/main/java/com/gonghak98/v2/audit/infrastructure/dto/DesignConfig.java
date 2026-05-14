package com.gonghak98.v2.audit.infrastructure.dto;

import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DesignConfig {

    private Map<Long, Integer> designPointByCourse;
    private Double minDesignPoint;
}
