package me.weekbelt.wetube.infra.util;

import me.weekbelt.wetube.modules.FileInfo.FileInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

@Component
public class FileUtils {

    @Value("${property.video.url}")
    private String VIDEO_PATH;
    @Value("${property.video.uploadVideoFolder}")
    private String UPLOAD_VIDEO;

    @Value("${property.image.url}")
    private String IMAGE_PATH;
    @Value("${property.image.uploadImageFolder}")
    private String UPLOAD_IMAGE;

    public FileInfo saveFileAtLocal(MultipartFile multipartFile) {
        String contentType = multipartFile.getContentType();
        String fileName = makeInherenceFile(multipartFile.getOriginalFilename());
        String saveFileName = "";

        assert contentType != null;
        if (contentType.contains("image")) {
            saveFileName += UPLOAD_IMAGE + fileName;
            saveFile(multipartFile, IMAGE_PATH + saveFileName);
        } else if (contentType.contains("video")) {
            saveFileName += UPLOAD_VIDEO + fileName;
            saveFile(multipartFile, VIDEO_PATH + saveFileName);
        }

        return FileInfo.builder()
                .contentType(contentType)
                .fileName(fileName)
                .saveFileName(saveFileName)
                .build();
    }

    private String makeInherenceFile(String originalName) {
        UUID uuid = UUID.randomUUID();
        return uuid.toString() + "_" + originalName;
    }

    private void saveFile(MultipartFile videoMultipartFile, String saveAddr) {
        try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(saveAddr));
             BufferedInputStream bufferedInputStream = new BufferedInputStream(videoMultipartFile.getInputStream())) {

            int readCount;
            byte[] buffer = new byte[1024];

            while ((readCount = bufferedInputStream.read(buffer)) != -1) {
                bufferedOutputStream.write(buffer, 0, readCount);
            }

        } catch (Exception e) {
            throw new RuntimeException("Video Save Error");
        }
    }
}
