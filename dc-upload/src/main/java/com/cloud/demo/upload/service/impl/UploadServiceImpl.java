package com.cloud.demo.upload.service.impl;

import com.cloud.demo.upload.service.UploadService;
import com.github.tobato.fastdfs.domain.FileInfo;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.domain.ThumbImageConfig;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

@Service("uploadService")
public class UploadServiceImpl implements UploadService {

    private static final Logger logger = LoggerFactory.getLogger(UploadServiceImpl.class);

    // 支持的文件类型
    private static final List<String> suffixes = Arrays.asList("image/png", "image/jpeg");

    private final FastFileStorageClient storageClient;

    private final ThumbImageConfig thumbImageConfig;

    @Autowired
    public UploadServiceImpl(FastFileStorageClient storageClient, ThumbImageConfig thumbImageConfig) {
        this.storageClient = storageClient;
        this.thumbImageConfig = thumbImageConfig;
    }

    @Override
    public String upload(MultipartFile file) {
        try{
            String PRE_PATH = "http://192.168.43.141/";
            // 1、图片信息校验
            // 1)校验文件类型
            String type = file.getContentType();
            if (!suffixes.contains(type)) {
                logger.info("上传失败，文件类型不匹配：{}", type);
                return null;
            }
            // 2)校验图片内容
            BufferedImage image = ImageIO.read(file.getInputStream());
            if (image == null) {
                logger.info("上传失败，文件内容不符合要求");
                return null;
            }
            // 2、将图片上传到FastDFS
            // 2.1、获取文件后缀名
            String extension = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
            // 2.2、上传
            //StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(), extension, null);
            StorePath storePath = storageClient.uploadImageAndCrtThumbImage(file.getInputStream(), file.getSize(), extension, null);
            // 2.3、返回完整路径
            //String thumbPath = PRE_PATH + storePath.getGroup() + "/" + thumbImageConfig.getThumbImagePath(storePath.getPath());
            return PRE_PATH + storePath.getFullPath();
            /*
            // 2、本地服务器保存图片
            // 2.1、生成保存目录
            File dir = new File("D:\\demo-cloud\\upload\\images");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            // 2.2、保存图片
            String saveFileName = file.getOriginalFilename();
            if (StringUtils.isEmpty(saveFileName) ) {
                return null;
            } else {
                saveFileName = saveFileName.substring(saveFileName.lastIndexOf(File.separator)+1);
            }
            file.transferTo(new File(dir, saveFileName));
            // 2.3、拼接图片地址
            return "http://image.demo.cloud.com/upload/" + file.getOriginalFilename();
            */
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Boolean delete(String fileUrl) {
        if (StringUtils.isEmpty(fileUrl)) {
            return false;
        }
        try {
            StorePath storePath = StorePath.praseFromUrl(fileUrl);
            // 删除指定图片
            storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
            try {
                // 删除对应的缩略图
                storageClient.deleteFile(storePath.getGroup(), thumbImageConfig.getThumbImagePath(storePath.getPath()));
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }
}
