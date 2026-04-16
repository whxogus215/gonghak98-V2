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

print(f"SQL 변환을 시작합니다. 결과 파일 : {sql_filename}")

with open(sql_filepath, 'w', encoding='utf-8') as sql_file:
  sql_file.write("-- 🚀 자동 생성된 마스터 데이터 마이그레이션 스크립트\n\n")

  # 1. 학과 INSERT SQL 변환
  sql_file.write("-- [1] DepartmentEntity 적재\n")
  department_csv = os.path.join(CSV_DIR, 'department.csv')

  if os.path.exists(department_csv):
    with open(department_csv, 'r', encoding='utf-8') as f:
      reader = csv.reader(f, delimiter='|')
      next(reader)  # 첫 줄 헤더 건너뛰기

      for row in reader:
        if not row: continue  # 빈 줄 건너뛰기
        name = row[0].strip()
        sql_file.write(
          f"INSERT INTO department_entity (name) VALUES ('{name}');\n")
  sql_file.write("\n")

  # 2. 과목 INSERT SQL 변환
  sql_file.write("-- [2] CourseEntity 적재\n")
  course_files = glob.glob(os.path.join(CSV_DIR, 'course', '*.csv'))
  # 파일명 기준 내림차순 정렬 (최신 학기 파일이 먼저 처리되도록)
  course_files.sort(reverse=True)

  course_codes = set()

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

        sql_file.write(
          f"INSERT INTO course_entity (code, name, credit) "
          f"VALUES ('{course_code}', '{course_name}', {credit});\n"
        )
  sql_file.write("\n")

  # 3. 공학인증 과목 INSERT SQL 변환
  sql_file.write("-- [3] GonghakCourseEntity 적재\n")
  gonghak_course_files = glob.glob(
    os.path.join(CSV_DIR, 'gonghak_course', '*.csv'))

  for g_file in gonghak_course_files:
    department_name = os.path.basename(g_file).replace('.csv', '')

    with open(g_file, 'r', encoding='utf-8') as f:
      reader = csv.reader(f, delimiter='|')
      next(reader)  # 첫 줄 헤더 건너뛰기

      for row in reader:
        if len(row) < 4: continue;
        course_name = row[1].strip().replace("'", "''")
        abeek_type = row[2].strip()
        course_type = row[3].strip()
        design_credit = float(row[4].strip())

        # 서브 쿼리를 활용해 FK 찾기
        sql_file.write(
          f"INSERT INTO gonghak_course_entity (department_id, course_id, abeek_type, course_type, design_credit) "
          f"VALUES ("
          f"(SELECT id FROM department_entity WHERE name = '{department_name}'),"
          f"(SELECT id FROM course_entity WHERE name = '{course_name}' LIMIT 1),"
          f"'{abeek_type}', "
          f"'{course_type}', "
          f"{design_credit}"
          f");\n"
        )
  sql_file.write("\n")

  # [4] 공학인증 요건 (GonghakRequirement JSON) 변환
  sql_file.write("-- [4] GonghakRequirementEntity 적재\n")
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
            f"INSERT INTO gonghak_requirement_entity (department_id, entrance_year, detail) "
            f"VALUES ("
            f"(SELECT id FROM department_entity WHERE name = '{dept_name}'), "
            f"{entrance_year}, "
            f"'{json_data}'"
            f");\n"
          )

print(f"✅ 변환 완료! {MIGRATION_DIR} 경로를 확인해주세요.")
