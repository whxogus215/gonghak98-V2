package com.gonghak98.v2.legacy.status.infrastructure;

import com.gonghak98.v2.legacy.common.domain.MajorsDomain;
import com.gonghak98.v2.legacy.status.domain.AbeekDomain;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbeekRepository extends JpaRepository<AbeekDomain, Long> {
    AbeekDomain save(AbeekDomain abeekDomain);
    List<AbeekDomain> findAllByYearAndMajorsDomain(int year, MajorsDomain majorsDomain);

}
