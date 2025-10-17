package com.gonghak98.v2.report.infrastructure.factory.dto;

import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DesignConfig {

    private Map<Integer, Integer> designPointByCourse;
    private Double minDesignPoint;
}
