package site.thanhtungle.fileuploaddownload.service;


import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface MinioStorageService {
    public void save(MultipartFile multipartFile, String fileName) throws Exception;

    public InputStream getInputStream(String fileName) throws Exception;
}
