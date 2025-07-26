package com.gonghak98.V2.status.infrastructure;

import com.gonghak98.V2.common.domain.MajorsDomain;
import com.gonghak98.V2.status.domain.AbeekDomain;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbeekRepository extends JpaRepository<AbeekDomain, Long> {
    AbeekDomain save(AbeekDomain abeekDomain);
    List<AbeekDomain> findAllByYearAndMajorsDomain(int year, MajorsDomain majorsDomain);

}
