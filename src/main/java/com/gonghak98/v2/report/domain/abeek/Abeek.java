package com.gonghak98.v2.report.domain.abeek;

import com.gonghak98.v2.report.domain.abeek.basic.Basic;
import com.gonghak98.v2.report.domain.abeek.design.Design;
import com.gonghak98.v2.report.domain.abeek.dto.CheckResult;
import com.gonghak98.v2.report.domain.abeek.gyoyang.Gyoyang;
import com.gonghak98.v2.report.domain.abeek.major.Major;
import com.gonghak98.v2.report.domain.abeek.prerequisite.Prerequisite;
import com.gonghak98.v2.report.domain.student.CompletedCourse;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Abeek {

    private final Gyoyang gyoyang;
    private final Basic basic;
    private final Major major;
    private final Design design;
    private final Prerequisite prerequisite;

    public CheckResult checkAllCourses(List<CompletedCourse> completedCourses) {
        CheckResult checkResult = new CheckResult(null, null);

        gyoyang.checkAllCourses(completedCourses, checkResult);
        basic.checkAllCourses(completedCourses, checkResult);
        major.checkAllCourses(completedCourses, checkResult);
        design.checkAllCourses(completedCourses, checkResult);
        prerequisite.checkAllCourses(completedCourses, checkResult);

        return checkResult;
    }

    // TODO 영역별 로직 구현 후, 최종 반영된 CheckResult로 영역별 CategoryResult를 만든 다음 AbeekResult를 반환하는 private 메서드 추가
    // TODO 도메인 용어 정리 -> 이름 변경
    // TODO 오브젝트 책 - 상속 대신 합성을 사용하라는 챕터 학습 및 정리하기! (블로그 포스팅 : gonghak98 V2 2편)
}
