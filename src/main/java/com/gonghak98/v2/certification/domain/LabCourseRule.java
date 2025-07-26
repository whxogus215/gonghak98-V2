package com.gonghak98.v2.certification.domain;

import java.util.Set;

public record LabCourseRule(Set<String> essentialLabCourseNames,
                            int minimumCount) {
}
