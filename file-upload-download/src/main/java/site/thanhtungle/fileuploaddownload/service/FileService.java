package site.thanhtungle.fileuploaddownload.service;

import org.springframework.web.multipart.MultipartFile;
import site.thanhtungle.fileuploaddownload.model.dto.DownloadFileDto;

import java.io.IOException;
import java.util.List;

public interface FileService {

    public List<String> uploadFiles(List<MultipartFile> multipartFiles) throws IOException;

    DownloadFileDto downloadFiles(String filename) throws IOException;
}
