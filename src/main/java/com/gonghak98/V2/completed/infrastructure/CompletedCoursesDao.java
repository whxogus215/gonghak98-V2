package com.gonghak98.V2.completed.infrastructure;

import com.gonghak98.V2.completed.domain.CompletedCoursesDomain;
import com.gonghak98.V2.user.domain.UserDomain;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompletedCoursesDao
    extends JpaRepository<CompletedCoursesDomain, Long>, CustomCompletedCoursesDao {
    
    List<CompletedCoursesDomain> findByUserDomain(UserDomain userDomain);
}
