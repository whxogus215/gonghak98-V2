-- 전자정보통신공학과 2022학년도 선후수 중 일반물리학및실험2 -> 전자기1이 아니라 일반물리학2 -> 전자기1로 변경
/*
변경 전
{
    "afterCode": "009649",
    "beforeCode": "002649"
}

변경 후
{
    "afterCode": "009649",
    "beforeCode": "002641"
}
*/

update gonghak_requirement
set detail = '{
  "totalRequirement": {
    "minCredit": 98
  },
  "designRequirement": {
    "minCredit": 9
  },
  "basicRequirement": {
    "minCredit": 30,
    "components": [
      {
        "name": "MSC_BASIC",
        "description": "주어진 MSC 과목 모두 이수",
        "ruleType": "MUST_TAKE_ALL",
        "conditionValue": 10,
        "targetCourses": [
          "011299",
          "001725",
          "001357",
          "002638",
          "002641",
          "000304",
          "000307",
          "002705",
          "011320",
          "008622"
        ]
      }
    ]
  },
  "majorRequirement": {
    "minCredit": 54,
    "components": [
      {
        "name": "LAB_MAJOR",
        "description": "실험교과목 최소 1과목 이수",
        "ruleType": "MIN_COUNT",
        "conditionValue": 1,
        "targetCourses": [
          "002647",
          "002649",
          "005611",
          "009658",
          "008076",
          "009661",
          "009666"
        ]
      },
      {
        "name": "GENERAL_MAJOR",
        "description": "일반영역 최소 24학점 이수",
        "ruleType": "MIN_CREDIT",
        "conditionValue": 24,
        "targetCourses": [
          "004114",
          "005246",
          "007620",
          "004111",
          "007453",
          "004474",
          "009649",
          "007806",
          "004699",
          "004600",
          "004829",
          "003284",
          "008086",
          "006294",
          "006132"
        ]
      }
    ]
  },
  "prerequisiteRequirement": {
    "targetCourses": [
      {
        "afterCode": "004111",
        "beforeCode": "010140"
      },
      {
        "afterCode": "009649",
        "beforeCode": "002641"
      },
      {
        "afterCode": "007722",
        "beforeCode": "007453"
      },
      {
        "afterCode": "009659",
        "beforeCode": "009649"
      },
      {
        "afterCode": "004600",
        "beforeCode": "005246"
      },
      {
        "afterCode": "004474",
        "beforeCode": "005246"
      }
    ]
  }
}
'
where id = 3;
