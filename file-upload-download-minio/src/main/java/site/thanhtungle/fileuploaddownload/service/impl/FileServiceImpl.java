package site.thanhtungle.fileuploaddownload.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.utils.FileNameUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import site.thanhtungle.fileuploaddownload.model.dto.DownloadFileDto;
import site.thanhtungle.fileuploaddownload.service.FileService;
import site.thanhtungle.fileuploaddownload.service.MinioStorageService;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final MinioStorageService minioStorageService;

    @Override
    public List<String> uploadFiles(List<MultipartFile> multipartFiles) throws Exception {
        return multipartFiles.stream().map(file -> {
            String fileName = "files/"
                    + UUID.randomUUID()
                    + "."
                    + FileNameUtils.getExtension(file.getOriginalFilename());
            try {
                minioStorageService.save(file, fileName);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return fileName;
        }).toList();
    }

    @Override
    public DownloadFileDto downloadFiles(String fileName) throws Exception {
        InputStream inputStream = minioStorageService.getInputStream(fileName);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("File-Name", fileName);
        httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;File-Name=" + fileName);

        return DownloadFileDto.builder()
                .inputStreamResource(new InputStreamResource(inputStream))
                .httpHeaders(httpHeaders)
                .build();
    }
}
