import csv
import glob
import os
from datetime import datetime

BASE_DIR = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
CSV_DIR = os.path.join(BASE_DIR, 'src', 'main', 'resources', 'csv')

MIGRATION_DIR = os.path.join(BASE_DIR, 'src', 'main', 'resources', 'db',
                             'migration')
os.makedirs(MIGRATION_DIR, exist_ok=True)

version = datetime.now().strftime("%Y%m%d_%H%M%S")
sql_filename = f"V{version}__init_master_data.sql"
sql_filepath = os.path.join(MIGRATION_DIR, sql_filename)

ABEEK_MAP = {
  '전문교양': 'GYOYANG',
  '전공': 'MAJOR',
  '설계': 'DESIGN'
}

COURSE_MAP = {
  '필수': 'ESSENTIAL',
  '선택': 'ELECTIVE',
  '기초설계': 'DESIGN_BASIC',
  '요소설계': 'DESIGN_ELEMENT',
  '종합설계': 'DESIGN_COMPREHENSIVE'
}

print(f"SQL 변환을 시작합니다. 결과 파일 : {sql_filename}")

with open(sql_filepath, 'w', encoding='utf-8') as sql_file:
  sql_file.write("-- 🚀 자동 생성된 마스터 데이터 마이그레이션 스크립트\n\n")

  # 1. 학과 INSERT SQL 변환
  sql_file.write("-- [1] department 테이블 적재\n")
  department_csv = os.path.join(CSV_DIR, 'department.csv')

  if os.path.exists(department_csv):
    with open(department_csv, 'r', encoding='utf-8') as f:
      reader = csv.reader(f, delimiter='|')
      next(reader)  # 첫 줄 헤더 건너뛰기

      for row in reader:
        if not row: continue  # 빈 줄 건너뛰기
        name = row[0].strip()
        sql_file.write(
          f"INSERT INTO department (name) VALUES ('{name}');\n")
  sql_file.write("\n")

  # 2. 과목 INSERT SQL 변환
  sql_file.write("-- [2] course 테이블\n")
  course_files = glob.glob(os.path.join(CSV_DIR, 'course', '*.csv'))
  # 파일명 기준 내림차순 정렬 (최신 학기 파일이 먼저 처리되도록)
  course_files.sort(reverse=True)

  course_codes = set()
  valid_course_names = set()

  for c_file in course_files:
    with open(c_file, 'r', encoding='utf-8') as f:
      reader = csv.reader(f, delimiter='|')
      next(reader)  # 첫 줄 헤더 건너뛰기

      for row in reader:
        if len(row) < 3: continue
        course_code = row[0].strip().strip('"')
        course_name = row[1].strip().replace("'", "''")
        credit = float(row[2].strip())

        if course_code in course_codes:
          continue
        course_codes.add(course_code)
        valid_course_names.add(course_name)

        sql_file.write(
          f"INSERT INTO course (code, name, credit) "
          f"VALUES ('{course_code}', '{course_name}', {credit});\n"
        )
  sql_file.write("\n")

  # 3-1. 공학인증 과목명과 실제 강의명과 일치하는지 사전 검증
  print("🔍 데이터 정합성 검사를 시작합니다...")
  mismatched_courses = set()

  gonghak_course_files = glob.glob(os.path.join(CSV_DIR, 'gonghak_course', '*.csv'))
  for g_file in gonghak_course_files:
    with open(g_file, 'r', encoding='utf-8') as f:
      reader = csv.reader(f, delimiter='|')
      next(reader)
      for row in reader:
        if len(row) < 4: continue
        target_course_name = row[1].strip()

        if target_course_name not in valid_course_names:
          mismatched_courses.add((os.path.basename(g_file), target_course_name))
  if mismatched_courses:
    print("\n🚨 [ERROR] 기초 과목(course) CSV에 존재하지 않는 과목이 공학인증 CSV에서 발견되었습니다!")
    print("SQL 생성을 중단합니다. 아래 과목들의 이름을 실제 홈페이지와 비교하여 CSV를 수정해 주세요.\n")
    
    for filename, course_name in sorted(mismatched_courses):
      print(f" - 파일 : {filename} / 매칭 실패 과목명 : '{course_name}'")
      
    exit(1)
  else:
    print("✅ 검증 통과! 매칭되지 않는 과목이 없습니다.\n")

  # 3-2. 공학인증 과목 INSERT SQL 변환
  sql_file.write("-- [3] gonghak_course 테이블 적재\n")

  for g_file in gonghak_course_files:
    department_name = os.path.basename(g_file).replace('.csv', '')

    with open(g_file, 'r', encoding='utf-8') as f:
      reader = csv.reader(f, delimiter='|')
      next(reader)  # 첫 줄 헤더 건너뛰기

      for row in reader:
        if len(row) < 5: continue;
        course_name = row[1].strip().replace("'", "''")
        abeek_type = ABEEK_MAP.get(row[2].strip(), row[2].strip())
        course_type = COURSE_MAP.get(row[3].strip(), row[3].strip())

        design_credit = float(row[4].strip())

        # 서브 쿼리를 활용해 FK 찾기
        sql_file.write(
          f"INSERT INTO gonghak_course (department_id, course_id, abeek_type, course_type, design_credit) "
          f"VALUES ("
          f"(SELECT id FROM department WHERE name = '{department_name}'),"
          f"(SELECT id FROM course WHERE name = '{course_name}' LIMIT 1),"
          f"'{abeek_type}', "
          f"'{course_type}', "
          f"{design_credit}"
          f");\n"
        )
  sql_file.write("\n")

  # 4. 공학인증 요건 (GonghakRequirement JSON) 변환
  sql_file.write("-- [4] gonghak_requirement 테이블 적재\n")
  JSON_DIR = os.path.join(BASE_DIR, 'src', 'main', 'resources', 'json',
                          'requirements')
  if os.path.exists(JSON_DIR):
    # 학과 폴더들 순회
    for dept_name in os.listdir(JSON_DIR):
      dept_path = os.path.join(JSON_DIR, dept_name)
      if not os.path.isdir(dept_path): continue

      for j_file in glob.glob(os.path.join(dept_path, '*.json')):
        # 파일명에서 입학년도 추출 (예: requirement_2025.json -> 2025)
        filename = os.path.basename(j_file)
        year_str = filename.split('_')[1].split('.')[0]
        entrance_year = int(year_str)

        with open(j_file, 'r', encoding='utf-8') as f:
          # JSON 파일의 내용을 하나의 문자열로 읽습니다. (DB의 JSON 타입 컬럼에 그대로 넣기 위함)
          json_data = f.read().strip().replace("'", "''")  # 작은따옴표 이스케이프

          sql_file.write(
            f"INSERT INTO gonghak_requirement (department_id, entrance_year, detail) "
            f"VALUES ("
            f"(SELECT id FROM department WHERE name = '{dept_name}'), "
            f"{entrance_year}, "
            f"'{json_data}'"
            f");\n"
          )

print(f"✅ 변환 완료! {MIGRATION_DIR} 경로를 확인해주세요.")
