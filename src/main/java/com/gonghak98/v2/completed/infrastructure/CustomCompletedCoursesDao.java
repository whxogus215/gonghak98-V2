package com.gonghak98.v2.completed.infrastructure;

import com.gonghak98.v2.completed.domain.CompletedCoursesDomain;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface CustomCompletedCoursesDao {

    @Transactional
    void saveAll(List<CompletedCoursesDomain> completedCourses);
}
