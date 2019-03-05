package com.cloud.demo.upload.service;


import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    String upload(MultipartFile file);
    Boolean delete(String fileUrl);
}
