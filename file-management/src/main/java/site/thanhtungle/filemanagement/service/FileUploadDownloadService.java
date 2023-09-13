package site.thanhtungle.filemanagement.service;

import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadDownloadService {

    public String fileUpload(MultipartFile file);

    public InputStreamResource downloadFile(String fileName);
}
