-- 1. department (학과) : 부모 테이블
DROP TABLE IF EXISTS department;
CREATE TABLE department (
	id SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(20) NOT NULL UNIQUE COMMENT '학과명'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 2. course (학교 전체 과목 테이블) : 부모 테이블
DROP TABLE IF EXISTS course;
CREATE TABLE course (
	id MEDIUMINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	code VARCHAR(20) NOT NULL UNIQUE COMMENT '학수번호',
	name VARCHAR(50) NOT NULL COMMENT '과목명',
	credit DOUBLE NOT NULL COMMENT '학점 (예: 3.0, 0.5)'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 3. gonghak_course (공학인증 과목 테이블) : 자식 테이블
DROP TABLE IF EXISTS gonghak_course;
CREATE TABLE gonghak_course (
	id MEDIUMINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	course_id MEDIUMINT UNSIGNED NOT NULL COMMENT '과목 ID',
	department_id SMALLINT UNSIGNED NOT NULL COMMENT '학과 ID',
	abeek_type VARCHAR(30) NOT NULL COMMENT 'ABEEK 교과구분 Enum 문자열',
	course_type VARCHAR(30) NOT NULL COMMENT '교과 인증구분 (예: 필수, 선택, 종합설계)',
	design_credit DOUBLE NOT NULL DEFAULT 0.0 COMMENT '설계 학점',

	-- 외래키 제약조건
	CONSTRAINT fk_gonghak_course_course_id FOREIGN KEY (course_id) REFERENCES course(id) ON DELETE CASCADE,
	CONSTRAINT fk_gonghak_course_department_id FOREIGN KEY (department_id) REFERENCES department(id) ON DELETE CASCADE,

	-- 복합 유니크 키: 공학인증 과목은 학과당 한 개만 가질 수 있음
	UNIQUE KEY uq_dept_course (department_id, course_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 4. gonghak_requirement
DROP TABLE IF EXISTS gonghak_requirement;
CREATE TABLE gonghak_requirement (
	id SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	department_id SMALLINT UNSIGNED NOT NULL COMMENT '학과 ID',
	entrance_year YEAR NOT NULL COMMENT '입학년도',
	detail JSON NOT NULL COMMENT '상세 요건 데이터 (영역별 최소 학점, 필수 과목 등)',

	-- 외래키 제약조건
	CONSTRAINT fk_gonghak_req_department_id FOREIGN KEY (department_id) REFERENCES department(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
