package com.cloud.demo.upload.controller;

import com.cloud.demo.common.LayUIData;
import com.cloud.demo.upload.service.UploadService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/upload")
@CrossOrigin
public class UploadController {

    private final UploadService uploadService;

    @Autowired
    public UploadController(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @RequestMapping("/image")
    public ResponseEntity<LayUIData> layUIImage(MultipartFile file){
        String url = uploadService.upload(file);
        if (StringUtils.isBlank(url)) {
            // url为空，证明上传失败
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // 返回200，并且携带url路径
        return ResponseEntity.ok(LayUIData.uploadResult(url));
    }

    @RequestMapping("/delete")
    public ResponseEntity<LayUIData> deleteImage(String fileUrl){
        if (!uploadService.delete(fileUrl)) {
            return ResponseEntity.ok().body(LayUIData.failure(HttpStatus.BAD_REQUEST.value(), "删除失败"));
        }
        return ResponseEntity.ok(LayUIData.commResult("OK"));
    }
}
