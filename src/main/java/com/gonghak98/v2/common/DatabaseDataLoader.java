package com.gonghak98.v2.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gonghak98.v2.report.domain.abeek.AbeekType;
import com.gonghak98.v2.report.domain.abeek.CourseType;
import com.gonghak98.v2.report.infrastructure.entity.CourseEntity;
import com.gonghak98.v2.report.infrastructure.entity.DepartmentEntity;
import com.gonghak98.v2.report.infrastructure.entity.GonghakCourseEntity;
import com.gonghak98.v2.report.infrastructure.entity.GonghakRequirementEntity;
import com.gonghak98.v2.report.infrastructure.factory.dto.RequirementDetail;
import com.gonghak98.v2.report.infrastructure.jpa.JpaCourseRepository;
import com.gonghak98.v2.report.infrastructure.jpa.JpaDepartmentRepository;
import com.gonghak98.v2.report.infrastructure.jpa.JpaGonghakCourseRepository;
import com.gonghak98.v2.report.infrastructure.jpa.JpaGonghakRequirementRepository;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Component;

@Slf4j
@Profile({"db"})
@RequiredArgsConstructor
@Component
public class DatabaseDataLoader implements ApplicationRunner {

    private static final String DELIMITER = "\\|";

    private final JpaDepartmentRepository jpaDepartmentRepository;
    private final JpaCourseRepository jpaCourseRepository;
    private final JpaGonghakCourseRepository jpaGonghakCourseRepository;
    private final JpaGonghakRequirementRepository jpaGonghakRequirementRepository;

    private final ObjectMapper objectMapper;

    @Override
    public void run(final ApplicationArguments args) {
        createDepartment();
        createCourse();
        createGonghakCourse();
        createGonghakRequirement();
    }

    private void createDepartment() {
        String path = "csv/department.csv";
        ClassPathResource resource = new ClassPathResource(path);
        if (!resource.exists()) {
            throw new IllegalArgumentException(path + "을 찾을 수 없습니다.");
        }

        try (BufferedReader br = Files.newBufferedReader(resource.getFile().toPath(), StandardCharsets.UTF_8)) {
            final String headerLine = br.readLine(); // 맨 첫 줄은 헤더이므로 사용하지 않습니다.

            String line;
            while ((line = br.readLine()) != null) {
                final String[] split = line.split(DELIMITER);
                final String name = split[0];

                jpaDepartmentRepository.save(new DepartmentEntity(name));
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private void createCourse() {
        String directoryPath = "csv/course";
        ClassPathResource resource = new ClassPathResource(directoryPath);
        if (!resource.exists()) {
            throw new IllegalArgumentException(directoryPath + "을 찾을 수 없습니다.");
        }

        try (DirectoryStream<Path> paths = Files.newDirectoryStream(resource.getFile().toPath())) {
            // CSV 파일 이름 내림차순 정렬 ex) 2026-1 => 2025-2
            List<Path> sortedPaths = StreamSupport.stream(paths.spliterator(), false)
                                                  .sorted(Comparator.comparing(Path::getFileName, Comparator.reverseOrder()))
                                                  .toList();
            for (Path path : sortedPaths) {
                log.info("과목 CSV 파일 읽기 시작 : {}", path);
                try (BufferedReader br = new BufferedReader(Files.newBufferedReader(path, StandardCharsets.UTF_8))) {
                    final String headerLine = br.readLine(); // 맨 첫 줄은 헤더이므로 사용하지 않습니다.

                    String line;
                    while ((line = br.readLine()) != null) {
                        final String[] split = line.split(DELIMITER);
                        try {
                            String courseCode = split[0].trim().replaceAll("^\"|\"$", ""); // ex) "예술,역사를만나다-근대와모던" -> 예술,역사를만나다-근대와모던
                            String name = split[1];
                            double credit = Double.parseDouble(split[2]);

                            if (jpaCourseRepository.existsByCode(courseCode)) {
                                continue;
                            }
                            jpaCourseRepository.save(new CourseEntity(courseCode, name, credit));
                        } catch (RuntimeException exception) {
                            log.info("변환에 실패한 데이터 : {}", line, exception);
                        }

                    }
                } catch (IOException e) {
                    throw new IllegalArgumentException(e);
                }
                log.info("과목 CSV 파일 읽기 종료 : {}", path);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createGonghakCourse() {
        String directoryPath = "csv/gonghak_course";
        ClassPathResource resource = new ClassPathResource(directoryPath);
        if (!resource.exists()) {
            throw new IllegalArgumentException(directoryPath + "을 찾을 수 없습니다.");
        }

        try (DirectoryStream<Path> paths = Files.newDirectoryStream(resource.getFile().toPath())) {
            for (Path path : paths) {
                processGonghakCourseEntity(path);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void processGonghakCourseEntity(final Path path) {
        log.info("공학인증 과목 CSV 파일 읽기 시작 : {}", path);
        String fileName = path.getFileName().toString();

        String departmentName = fileName.substring(0, fileName.lastIndexOf('.'));
        DepartmentEntity department = jpaDepartmentRepository.findByName(departmentName)
                                                             .orElseThrow(() -> new IllegalArgumentException("해당 이름의 학과가 존재하지 않습니다."));
        try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            final String headerLine = br.readLine(); // 맨 첫 줄은 헤더이므로 사용하지 않습니다.

            String line;
            while ((line = br.readLine()) != null) {
                parseAndSaveLine(line, department, departmentName);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        log.info("공학인증 과목 CSV 파일 읽기 종료 : {}", path);
    }

    private void parseAndSaveLine(final String line, final DepartmentEntity department, final String departmentName) {
        String[] split = line.split(DELIMITER);
        String courseName = "";
        String areaType = "";
        String courseType = "";

        try {
            courseName = split[1];
            areaType = split[2];
            courseType = split[3];

            CourseEntity courseEntity = jpaCourseRepository.findByName(courseName)
                                                           .orElseThrow(() -> new IllegalArgumentException("해당 과목이 존재하지 않습니다."));
            jpaGonghakCourseRepository.save(new GonghakCourseEntity(department,
                                                                    AbeekType.getByName(areaType),
                                                                    CourseType.getByName(courseType),
                                                                    courseEntity));
        } catch (NumberFormatException ne) {
            log.info("변환에 실패한 데이터 : {}", line);
            log.error(ne.getMessage());
        } catch (IncorrectResultSizeDataAccessException dae) {
            log.info("현재 course_entity 테이블에 '{}'라는 과목이 2개 이상 존재합니다. {}의 공학인증 요건표를 참고하여 수동으로 데이터를 추가해주세요.", courseName, departmentName);
        } catch (RuntimeException re) {
            log.info("변환에 실패한 데이터 : {}", line);
            log.error("변환 중 예외 발생", re);
        }
    }

    private void createGonghakRequirement() {
        String directoryPath = "json/requirements";
        ClassPathResource resource = new ClassPathResource(directoryPath);
        if (!resource.exists()) {
            throw new IllegalArgumentException(directoryPath + "을 찾을 수 없습니다.");
        }

        try (DirectoryStream<Path> departmentDirs = Files.newDirectoryStream(resource.getFile().toPath())) {
            for (Path dirPath : departmentDirs) {
                if (!Files.isDirectory(dirPath)) {
                    continue;
                }

                String departmentName = dirPath.getFileName().toString();
                DepartmentEntity department = jpaDepartmentRepository.findByName(departmentName)
                                                                     .orElseThrow(
                                                                         () -> new IllegalArgumentException("해당 이름의 학과가 존재하지 않습니다: " + departmentName));

                try (DirectoryStream<Path> jsonFiles = Files.newDirectoryStream(dirPath, "*.json")) {
                    for (Path jsonPath : jsonFiles) {
                        log.info("공학인증 요건 JSON 파일 읽기 시작 : {}", jsonPath);
                        try (BufferedReader br = new BufferedReader(Files.newBufferedReader(jsonPath, StandardCharsets.UTF_8))) {
                            String fileName = jsonPath.getFileName().toString();
                            String[] parts = fileName.split("_");
                            Short entranceYear = Short.parseShort(parts[1].substring(0, parts[1].indexOf(".")));
                            RequirementDetail detail = objectMapper.readValue(br, RequirementDetail.class);

                            jpaGonghakRequirementRepository.save(new GonghakRequirementEntity(department, entranceYear, detail));
                        } catch (IOException e) {
                            throw new IllegalArgumentException(e);
                        }
                        log.info("공학인증 요건 JSON 파일 읽기 종료 : {}", jsonPath);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
