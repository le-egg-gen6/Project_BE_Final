package com.myproject.project_oop.controller;

import com.myproject.project_oop.dto.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("${api.prefix}" + "${api.version}" + "/file")
@RequiredArgsConstructor
public class FileController {
    private static String imageDirectory = System.getProperty("user.dir") + "/images/";

    @RequestMapping(value = "/upload", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE, "application/json"})
    public ResponseEntity<BaseResponse<?>> uploadImage(@RequestParam("imageFile") MultipartFile file,
                                                    @RequestParam("imageName") String name) {
        makeDirectoryIfNotExist(imageDirectory);
        Path fileNamePath = Paths.get(imageDirectory,
                name.concat(".").concat(FilenameUtils.getExtension(file.getOriginalFilename())));
        try {
            Files.write(fileNamePath, file.getBytes());
            return ResponseEntity.ok(BaseResponse.buildMessageResponse("Upload success!"));
        } catch (IOException ex) {
            return ResponseEntity.ok(BaseResponse.buildErrorResponse("Image is not uploaded"));
        }
    }

    private void makeDirectoryIfNotExist(String imageDirectory) {
        File directory = new File(imageDirectory);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }
}
