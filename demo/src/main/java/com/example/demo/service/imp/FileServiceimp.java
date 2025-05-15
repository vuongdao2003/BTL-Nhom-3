package com.example.demo.service.imp;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface FileServiceimp {
    boolean saveFile(MultipartFile file);
    Resource loadFile(String filename);
}
