-- [1] department 테이블 적재
INSERT INTO department (name) VALUES ('항공우주공학과');

-- [2] gonghak_course 테이블 적재
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = 'English Listening Practice 1' LIMIT 1),'GYOYANG', 'ESSENTIAL', 0.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = 'English Reading Practice 1' LIMIT 1),'GYOYANG', 'ESSENTIAL', 0.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '대학생활과진로탐색' LIMIT 1),'GYOYANG', 'ESSENTIAL', 0.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '대학영어' LIMIT 1),'GYOYANG', 'ESSENTIAL', 0.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '문제해결을위한글쓰기와발표' LIMIT 1),'GYOYANG', 'ESSENTIAL', 0.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '서양철학:쟁점과토론' LIMIT 1),'GYOYANG', 'ESSENTIAL', 0.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '세계사:인간과문명' LIMIT 1),'GYOYANG', 'ESSENTIAL', 0.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '세종사회봉사1' LIMIT 1),'GYOYANG', 'ESSENTIAL', 0.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '경영학' LIMIT 1),'GYOYANG', 'ELECTIVE', 0.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '경제학' LIMIT 1),'GYOYANG', 'ELECTIVE', 0.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '동서양의사상과윤리' LIMIT 1),'GYOYANG', 'ELECTIVE', 0.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '세계사' LIMIT 1),'GYOYANG', 'ELECTIVE', 0.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '융합예술의이해' LIMIT 1),'GYOYANG', 'ELECTIVE', 0.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '컴퓨터게임과메타버스' LIMIT 1),'GYOYANG', 'ELECTIVE', 0.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '고급프로그래밍입문-C' LIMIT 1),'MSC', 'ESSENTIAL', 0.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '공업수학1' LIMIT 1),'MSC', 'ESSENTIAL', 0.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '공업수학2' LIMIT 1),'MSC', 'ESSENTIAL', 0.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '다변수미적분학' LIMIT 1),'MSC', 'ESSENTIAL', 0.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '소프트웨어기초코딩' LIMIT 1),'MSC', 'ESSENTIAL', 0.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '일반물리학및실험1' LIMIT 1),'MSC', 'ESSENTIAL', 0.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '일반물리학및실험2' LIMIT 1),'MSC', 'ESSENTIAL', 0.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '일반화학' LIMIT 1),'MSC', 'ESSENTIAL', 0.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '일변수미적분학' LIMIT 1),'MSC', 'ESSENTIAL', 0.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '고체역학' LIMIT 1),'MAJOR', 'ESSENTIAL', 0.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '공업역학' LIMIT 1),'MAJOR', 'ESSENTIAL', 0.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '동역학' LIMIT 1),'MAJOR', 'ESSENTIAL', 0.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '비행동역학' LIMIT 1),'MAJOR', 'ESSENTIAL', 0.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '열역학' LIMIT 1),'MAJOR', 'ESSENTIAL', 0.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '이상유체역학' LIMIT 1),'MAJOR', 'ESSENTIAL', 0.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '항공우주공학개론' LIMIT 1),'MAJOR', 'ESSENTIAL', 0.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '선형대수학' LIMIT 1),'MAJOR', 'ELECTIVE', 0.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '수치해석' LIMIT 1),'MAJOR', 'ELECTIVE', 0.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '압축성유체역학' LIMIT 1),'MAJOR', 'ELECTIVE', 0.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '열전달' LIMIT 1),'MAJOR', 'ELECTIVE', 0.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '우주궤도역학' LIMIT 1),'MAJOR', 'ELECTIVE', 0.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '인공위성응용' LIMIT 1),'MAJOR', 'ELECTIVE', 0.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '자동제어' LIMIT 1),'MAJOR', 'ELECTIVE', 0.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '자율비행체시스템설계1' LIMIT 1),'MAJOR', 'ELECTIVE', 0.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '자율비행체시스템설계2' LIMIT 1),'MAJOR', 'ELECTIVE', 0.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '전산구조해석및실습' LIMIT 1),'MAJOR', 'ELECTIVE', 0.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '졸업연구및진로2' LIMIT 1),'MAJOR', 'ELECTIVE', 0.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '졸업연구및진로1' LIMIT 1),'MAJOR', 'ELECTIVE', 0.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '진동학' LIMIT 1),'MAJOR', 'ELECTIVE', 0.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '추진체공학' LIMIT 1),'MAJOR', 'ELECTIVE', 0.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '항공기구조역학' LIMIT 1),'MAJOR', 'ELECTIVE', 0.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '항공센서신호처리' LIMIT 1),'MAJOR', 'ELECTIVE', 0.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '항공우주공학연구1' LIMIT 1),'MAJOR', 'ELECTIVE', 0.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '항공우주공학연구2' LIMIT 1),'MAJOR', 'ELECTIVE', 0.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '항공우주응용SW1' LIMIT 1),'MAJOR', 'ELECTIVE', 0.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '항공우주응용SW2' LIMIT 1),'MAJOR', 'ELECTIVE', 0.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '항공제도및CAD' LIMIT 1),'MAJOR', 'ELECTIVE', 0.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '기초설계' LIMIT 1),'DESIGN', 'DESIGN_BASIC', 3.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '로켓공학및설계' LIMIT 1),'DESIGN', 'DESIGN_ELEMENT', 1.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '메카트로닉스종합설계' LIMIT 1),'DESIGN', 'DESIGN_ELEMENT', 1.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '무인항공기설계1' LIMIT 1),'DESIGN', 'DESIGN_ELEMENT', 1.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '무인항공기설계2' LIMIT 1),'DESIGN', 'DESIGN_ELEMENT', 1.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '시뮬레이션시스템설계' LIMIT 1),'DESIGN', 'DESIGN_ELEMENT', 1.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '유도제어시스템설계' LIMIT 1),'DESIGN', 'DESIGN_ELEMENT', 1.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '응용공기역학및설계' LIMIT 1),'DESIGN', 'DESIGN_ELEMENT', 1.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '항공기설계' LIMIT 1),'DESIGN', 'DESIGN_ELEMENT', 1.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '항법전자시스템설계' LIMIT 1),'DESIGN', 'DESIGN_ELEMENT', 1.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '헬리콥터공학및설계' LIMIT 1),'DESIGN', 'DESIGN_ELEMENT', 1.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '종합설계1' LIMIT 1),'DESIGN', 'DESIGN_COMPREHENSIVE', 3.0);
INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'),(SELECT id FROM course WHERE name = '종합설계2' LIMIT 1),'DESIGN', 'DESIGN_COMPREHENSIVE', 3.0);

-- [3] gonghak_requirement 테이블 적재
INSERT INTO gonghak_requirement (department_id, entrance_year, detail) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'), 2020, '{
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
        "conditionValue": 9,
        "targetCourses": [
          "010140",
          "010141",
          "000304",
          "000307",
          "002647",
          "002649",
          "002703",
          "009799",
          "009791"
        ]
      },
      {
        "name": "MSC_BASIC",
        "description": "주어진 MSC 과목 중 1개 이상 이수",
        "ruleType": "MIN_COUNT",
        "conditionValue": 1,
        "targetCourses": [
          "001727",
          "004102"
        ]
      }
    ]
  },
  "majorRequirement": {
    "minCredit": 54,
    "components": [
      {
        "name": "MAJOR",
        "description": "전공필수 과목 모두 이수",
        "ruleType": "MUST_TAKE_ALL",
        "conditionValue": 7,
        "targetCourses": [
          "004325",
          "004714",
          "004642",
          "006885",
          "004510",
          "004513",
          "008297"
        ]
      }
    ]
  },
  "prerequisiteRequirement": {
    "targetCourses": [
      {
        "afterCode": "010141",
        "beforeCode": "010140"
      },
      {
        "afterCode": "000307",
        "beforeCode": "000304"
      },
      {
        "afterCode": "002649",
        "beforeCode": "002647"
      },
      {
        "afterCode": "004714",
        "beforeCode": "002647"
      },
      {
        "afterCode": "004642",
        "beforeCode": "002647"
      },
      {
        "afterCode": "004510",
        "beforeCode": "004325"
      },
      {
        "afterCode": "004755",
        "beforeCode": "004510"
      },
      {
        "afterCode": "008115",
        "beforeCode": "004714"
      },
      {
        "afterCode": "006887",
        "beforeCode": "004642"
      },
      {
        "afterCode": "004756",
        "beforeCode": "004513"
      }
    ]
  }
}');
INSERT INTO gonghak_requirement (department_id, entrance_year, detail) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'), 2021, '{
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
        "conditionValue": 9,
        "targetCourses": [
          "010140",
          "010141",
          "000304",
          "000307",
          "002647",
          "002649",
          "002703",
          "009799",
          "009791"
        ]
      },
      {
        "name": "MSC_BASIC",
        "description": "주어진 MSC 과목 중 1개 이상 이수",
        "ruleType": "MIN_COUNT",
        "conditionValue": 1,
        "targetCourses": [
          "001727",
          "004102"
        ]
      }
    ]
  },
  "majorRequirement": {
    "minCredit": 54,
    "components": [
      {
        "name": "MAJOR",
        "description": "전공필수 과목 모두 이수",
        "ruleType": "MUST_TAKE_ALL",
        "conditionValue": 7,
        "targetCourses": [
          "004325",
          "004714",
          "004642",
          "006885",
          "004510",
          "004513",
          "008297"
        ]
      },
      {
        "name": "MAJOR",
        "description": "실험실습 과목 1개 이상 이수",
        "ruleType": "MIN_COUNT",
        "conditionValue": 1,
        "targetCourses": [
          "010921",
          "010922",
          "009833",
          "010662",
          "007161",
          "007164",
          "007055",
          "006887"
        ]
      }
    ]
  },
  "prerequisiteRequirement": {
    "targetCourses": [
      {
        "afterCode": "010141",
        "beforeCode": "010140"
      },
      {
        "afterCode": "000307",
        "beforeCode": "000304"
      },
      {
        "afterCode": "002649",
        "beforeCode": "002647"
      },
      {
        "afterCode": "004714",
        "beforeCode": "002647"
      },
      {
        "afterCode": "004642",
        "beforeCode": "002647"
      },
      {
        "afterCode": "004510",
        "beforeCode": "004325"
      },
      {
        "afterCode": "004755",
        "beforeCode": "004510"
      },
      {
        "afterCode": "008115",
        "beforeCode": "004714"
      },
      {
        "afterCode": "006887",
        "beforeCode": "004642"
      },
      {
        "afterCode": "004756",
        "beforeCode": "004513"
      }
    ]
  }
}');
INSERT INTO gonghak_requirement (department_id, entrance_year, detail) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'), 2022, '{
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
        "conditionValue": 9,
        "targetCourses": [
          "001357",
          "001362",
          "000304",
          "000307",
          "002638",
          "002641",
          "002705",
          "011298",
          "011299"
        ]
      },
      {
        "name": "MSC_BASIC",
        "description": "주어진 MSC 과목 중 1개 이상 이수",
        "ruleType": "MIN_COUNT",
        "conditionValue": 1,
        "targetCourses": [
          "001727",
          "004102"
        ]
      }
    ]
  },
  "majorRequirement": {
    "minCredit": 54,
    "components": [
      {
        "name": "MAJOR",
        "description": "전공필수 과목 모두 이수",
        "ruleType": "MUST_TAKE_ALL",
        "conditionValue": 7,
        "targetCourses": [
          "004325",
          "004714",
          "004642",
          "006885",
          "004510",
          "004513",
          "008297"
        ]
      },
      {
        "name": "MAJOR",
        "description": "실험실습 과목 1개 이상 이수",
        "ruleType": "MIN_COUNT",
        "conditionValue": 1,
        "targetCourses": [
          "010921",
          "010922",
          "009833",
          "010662",
          "007161",
          "007164",
          "007055",
          "006887"
        ]
      }
    ]
  },
  "prerequisiteRequirement": {
    "targetCourses": [
      {
        "afterCode": "001362",
        "beforeCode": "001357"
      },
      {
        "afterCode": "000307",
        "beforeCode": "000304"
      },
      {
        "afterCode": "002641",
        "beforeCode": "002638"
      },
      {
        "afterCode": "004714",
        "beforeCode": "002638"
      },
      {
        "afterCode": "004642",
        "beforeCode": "002638"
      },
      {
        "afterCode": "004510",
        "beforeCode": "004325"
      },
      {
        "afterCode": "004755",
        "beforeCode": "004510"
      },
      {
        "afterCode": "004756",
        "beforeCode": "004513"
      },
      {
        "afterCode": "008115",
        "beforeCode": "004714"
      },
      {
        "afterCode": "006887",
        "beforeCode": "004642"
      }
    ]
  }
}');
INSERT INTO gonghak_requirement (department_id, entrance_year, detail) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'), 2023, '{
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
        "conditionValue": 9,
        "targetCourses": [
          "001357",
          "001362",
          "000304",
          "000307",
          "002638",
          "002641",
          "002705",
          "011298",
          "011299"
        ]
      },
      {
        "name": "MSC_BASIC",
        "description": "주어진 MSC 과목 중 1개 이상 이수",
        "ruleType": "MIN_COUNT",
        "conditionValue": 1,
        "targetCourses": [
          "001727",
          "004102"
        ]
      }
    ]
  },
  "majorRequirement": {
    "minCredit": 54,
    "components": [
      {
        "name": "MAJOR",
        "description": "전공필수 과목 모두 이수",
        "ruleType": "MUST_TAKE_ALL",
        "conditionValue": 6,
        "targetCourses": [
          "011360",
          "004714",
          "004642",
          "006885",
          "004510",
          "011398"
        ]
      },
      {
        "name": "MAJOR",
        "description": "실험실습 과목 1개 이상 이수",
        "ruleType": "MIN_COUNT",
        "conditionValue": 1,
        "targetCourses": [
          "010921",
          "010922",
          "009833",
          "010662",
          "007161",
          "007164",
          "007055",
          "006887"
        ]
      }
    ]
  },
  "prerequisiteRequirement": {
    "targetCourses": [
      {
        "afterCode": "001362",
        "beforeCode": "001357"
      },
      {
        "afterCode": "000307",
        "beforeCode": "000304"
      },
      {
        "afterCode": "002641",
        "beforeCode": "002638"
      },
      {
        "afterCode": "004714",
        "beforeCode": "002638"
      },
      {
        "afterCode": "004642",
        "beforeCode": "002638"
      },
      {
        "afterCode": "004755",
        "beforeCode": "004510"
      },
      {
        "afterCode": "004756",
        "beforeCode": "011398"
      },
      {
        "afterCode": "008115",
        "beforeCode": "004714"
      },
      {
        "afterCode": "006887",
        "beforeCode": "004642"
      }
    ]
  }
}');
INSERT INTO gonghak_requirement (department_id, entrance_year, detail) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'), 2024, '{
  "totalRequirement": {
    "minCredit": 98
  },
  "designRequirement": {
    "minCredit": 9
  },
  "basicRequirement": {
    "minCredit": 27,
    "components": [
      {
        "name": "MSC_BASIC",
        "description": "주어진 MSC 과목 모두 이수",
        "ruleType": "MUST_TAKE_ALL",
        "conditionValue": 8,
        "targetCourses": [
          "001357",
          "001362",
          "000304",
          "000307",
          "002638",
          "002705",
          "011298",
          "011300"
        ]
      },
      {
        "name": "MSC_BASIC",
        "description": "주어진 MSC 과목 중 1개 이상 이수",
        "ruleType": "MIN_COUNT",
        "conditionValue": 1,
        "targetCourses": [
          "001727",
          "004102"
        ]
      }
    ]
  },
  "majorRequirement": {
    "minCredit": 45,
    "components": [
      {
        "name": "MAJOR",
        "description": "전공필수 과목 모두 이수",
        "ruleType": "MUST_TAKE_ALL",
        "conditionValue": 6,
        "targetCourses": [
          "011360",
          "004714",
          "004642",
          "006885",
          "004510",
          "011398"
        ]
      },
      {
        "name": "MAJOR",
        "description": "실험실습 과목 1개 이상 이수",
        "ruleType": "MIN_COUNT",
        "conditionValue": 1,
        "targetCourses": [
          "010921",
          "010922",
          "011406",
          "010662",
          "007164",
          "007055",
          "006887",
          "008115",
          "011405",
          "008140"
        ]
      }
    ]
  },
  "prerequisiteRequirement": {
    "targetCourses": [
      {
        "afterCode": "001362",
        "beforeCode": "001357"
      },
      {
        "afterCode": "000307",
        "beforeCode": "000304"
      },
      {
        "afterCode": "002705",
        "beforeCode": "002638"
      },
      {
        "afterCode": "004714",
        "beforeCode": "002638"
      },
      {
        "afterCode": "004642",
        "beforeCode": "002638"
      },
      {
        "afterCode": "004755",
        "beforeCode": "004510"
      },
      {
        "afterCode": "004756",
        "beforeCode": "011398"
      },
      {
        "afterCode": "008115",
        "beforeCode": "004714"
      },
      {
        "afterCode": "006887",
        "beforeCode": "004642"
      }
    ]
  }
}');
INSERT INTO gonghak_requirement (department_id, entrance_year, detail) VALUES ((SELECT id FROM department WHERE name = '항공우주공학과'), 2025, '{
  "totalRequirement": {
    "minCredit": 98
  },
  "designRequirement": {
    "minCredit": 9
  },
  "basicRequirement": {
    "minCredit": 27,
    "components": [
      {
        "name": "MSC_BASIC",
        "description": "주어진 MSC 과목 모두 이수",
        "ruleType": "MUST_TAKE_ALL",
        "conditionValue": 8,
        "targetCourses": [
          "001357",
          "001362",
          "000304",
          "000307",
          "002638",
          "002705",
          "011298",
          "011300"
        ]
      },
      {
        "name": "MSC_BASIC",
        "description": "주어진 MSC 과목 중 1개 이상 이수",
        "ruleType": "MIN_COUNT",
        "conditionValue": 1,
        "targetCourses": [
          "001727",
          "004102"
        ]
      }
    ]
  },
  "majorRequirement": {
    "minCredit": 45,
    "components": [
      {
        "name": "MAJOR",
        "description": "전공필수 과목 모두 이수",
        "ruleType": "MUST_TAKE_ALL",
        "conditionValue": 6,
        "targetCourses": [
          "011360",
          "004714",
          "004642",
          "006885",
          "004510",
          "011398"
        ]
      },
      {
        "name": "MAJOR",
        "description": "실험실습 과목 1개 이상 이수",
        "ruleType": "MIN_COUNT",
        "conditionValue": 1,
        "targetCourses": [
          "010921",
          "010922",
          "011406",
          "010662",
          "007164",
          "007055",
          "006887",
          "008115",
          "011405",
          "008140"
        ]
      }
    ]
  },
  "prerequisiteRequirement": {
    "targetCourses": [
      {
        "afterCode": "001362",
        "beforeCode": "001357"
      },
      {
        "afterCode": "000307",
        "beforeCode": "000304"
      },
      {
        "afterCode": "002705",
        "beforeCode": "002638"
      },
      {
        "afterCode": "004714",
        "beforeCode": "002638"
      },
      {
        "afterCode": "004642",
        "beforeCode": "002638"
      },
      {
        "afterCode": "004755",
        "beforeCode": "004510"
      },
      {
        "afterCode": "004756",
        "beforeCode": "011398"
      },
      {
        "afterCode": "008115",
        "beforeCode": "004714"
      },
      {
        "afterCode": "006887",
        "beforeCode": "004642"
      }
    ]
  }
}');
