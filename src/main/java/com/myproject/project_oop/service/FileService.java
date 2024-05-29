package com.myproject.project_oop.service;

import com.myproject.project_oop.model.File;
import com.myproject.project_oop.model.constant.FileType;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface FileService {

    File save(File file);

}
