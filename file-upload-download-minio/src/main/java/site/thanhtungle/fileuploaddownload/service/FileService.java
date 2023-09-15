package site.thanhtungle.fileuploaddownload.service;

import org.springframework.web.multipart.MultipartFile;
import site.thanhtungle.fileuploaddownload.model.dto.DownloadFileDto;

import java.util.List;

public interface FileService {

    public List<String> uploadFiles(List<MultipartFile> multipartFiles) throws Exception;

    DownloadFileDto downloadFiles(String fileName) throws Exception;
}
