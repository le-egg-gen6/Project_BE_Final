package com.myproject.project_oop.service.impl;

import com.myproject.project_oop.model.File;
import com.myproject.project_oop.model.constant.FileType;
import com.myproject.project_oop.repository.FileRepository;
import com.myproject.project_oop.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    @Override
    public File save(File file) {
        return fileRepository.save(file);
    }
}
