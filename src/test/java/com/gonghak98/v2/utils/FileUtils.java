package com.gonghak98.v2.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;

@Slf4j
public class FileUtils {

    private FileUtils() {
    }

    public static MockMultipartFile 업로드_파일_생성(String path) {
        String fileName = "기이수성적조회";
        ClassPathResource resource = new ClassPathResource(path);
        try (FileInputStream fis = new FileInputStream(resource.getFile())){
            return new MockMultipartFile(fileName, resource.getFilename(), "xlsx", fis);
        } catch (IOException e) {
            log.warn("파일을 찾을 수 없습니다. path : {}", path, e);
            throw new RuntimeException(e);
        }
    }

    public static File 인수테스트_업로드_파일_생성(String filePath) throws IOException {
        return new ClassPathResource(filePath).getFile();
    }
}
