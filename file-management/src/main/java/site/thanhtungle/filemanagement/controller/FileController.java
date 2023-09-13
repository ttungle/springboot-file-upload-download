package site.thanhtungle.filemanagement.controller;

import jakarta.servlet.ServletContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.thanhtungle.filemanagement.service.FileUploadDownloadService;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/files")
@Slf4j
@AllArgsConstructor
public class FileController {

    private FileUploadDownloadService fileUploadDownloadService;

    private ServletContext context;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file")MultipartFile file) {
        return fileUploadDownloadService.fileUpload(file);
    }

    @PostMapping("/uploadMultiple")
    public List<String> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.stream(files)
                .map(file -> fileUploadDownloadService.fileUpload(file))
                .toList();
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable("fileName") String fileName) {
        log.info("Downloading file {} ", fileName);
        InputStreamResource resource = fileUploadDownloadService.downloadFile(fileName);
        return ResponseEntity.ok()
                .contentType(getMediaTypeForFileName(context, fileName))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName)
                .body(resource);

    }

    private MediaType getMediaTypeForFileName(ServletContext servletContext, String fileName) {
        // application/pdf
        // application/xml
        // image/gif, ...

        String mineType = servletContext.getMimeType(fileName);
        try {
            return MediaType.parseMediaType(mineType);
        } catch (Exception e) {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
}
