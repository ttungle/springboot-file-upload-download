package site.thanhtungle.fileuploaddownload.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import site.thanhtungle.fileuploaddownload.model.dto.DownloadFileDto;
import site.thanhtungle.fileuploaddownload.service.FileService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import static java.nio.file.Files.copy;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
public class FileServiceImpl implements FileService {

    @Value("${file.storage.location}")
    private String DIRECTORY;

    @Override
    public List<String> uploadFiles(List<MultipartFile> multipartFiles) throws IOException {
        return multipartFiles.stream().map(file -> {
            String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            Path fileStorage = get(DIRECTORY, filename).toAbsolutePath().normalize();
            try {
                copy(file.getInputStream(), fileStorage, REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return filename;
        }).toList();
    }

    @Override
    public DownloadFileDto downloadFiles(String filename) throws IOException {
        Path filePath = get(DIRECTORY).toAbsolutePath().normalize().resolve(filename);
        if (!Files.exists(filePath)) {
            throw new FileNotFoundException(filename + " was not found on the server");
        }
        Resource resource = new UrlResource(filePath.toUri());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("File-Name", filename);
        httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;File-Name=" + resource.getFilename());

        return DownloadFileDto.builder()
                .filePath(filePath)
                .resource(resource)
                .httpHeaders(httpHeaders)
                .build();
    }
}
