package com.gonghak98.v2.file.service;

import com.gonghak98.v2.file.service.dto.FileResponse;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    FileResponse getFileData(MultipartFile file);
}
