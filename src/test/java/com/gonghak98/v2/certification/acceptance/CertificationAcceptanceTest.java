package com.gonghak98.v2.certification.acceptance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CertificationAcceptanceTest {

    @Test
    @DisplayName("전자정보통신공학과 사용자는 최신년도 기준으로 자신의 ABEEK 진행도를 조회할 수 있다.")
    /*
    * given 사용자가 로그인 상태여야 한다.
        and 사용자가 기이수 성적파일을 업로드 한 상태여야 한다.
      when 진행현황 조회를 요청한다.
      then 200 Status Code를 응답한다.
        and 전문교양, MSC, 전공, 인증최소이수학점에 대한 최소 이수 학점, 현재 이수 학점 정보를 조회한다.
    * */
    void ABEEK_교과구분_진행현황_조회() {
        
        /*
        * GonghakAbeekService
        *   - CompletedCourseService(기이수 성적 파일 가져와야 함)
        *   - LatestAbeekStandardService(최신년도 공학인증 기준 데이터 가져와야 함)
        *   - AbeekDivision(이름 바꾸기)CertificationService(학과의 ABEEK 교과구분별 검사 로직 수행)
        *     - 전문교양 영역 처리
        *     - BSM/MSC 영역 처리
        *     - 전공 + 설계 영역 처리
        *     TODO: 전공+설계 영역 TDD 구현하기
        * */
        
        //given
        /*
        * 로그인(Member 도메인) API 요청
        * 기이수파일 업로드(CompletedCourse(이름 바꾸기) 도메인) API 요청
        * */
        
        //when
        /* ABEEK 결과 조회 요청 -> GonghakAbeekService -> 도메인 설계 필요
        * */
        
        //then
        /* 200 응답코드
           전문교양, MSC< 전공, 인증최소이수학점에 대한 최소 이수학점 및 현재 이수학점 정보 반환
        * */
    }
}
