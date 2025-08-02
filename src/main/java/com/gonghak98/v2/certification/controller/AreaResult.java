package com.gonghak98.v2.certification.controller;

public record AreaResult(double userPoint,
                         double minPoint,
                         boolean isPassed,
                         AreaDetails details) {

}
