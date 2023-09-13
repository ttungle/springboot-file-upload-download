package site.thanhtungle.fileuploaddownload.controller;

import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.thanhtungle.fileuploaddownload.model.dto.DownloadFileDto;
import site.thanhtungle.fileuploaddownload.service.FileService;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@RestController
@RequestMapping("/files")
@AllArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("/uploads")
    public ResponseEntity<List<String>> uploadFiles(@RequestParam("files")List<MultipartFile> multipartFiles) throws IOException {
        List<String> fileNames = fileService.uploadFiles(multipartFiles);
        return ResponseEntity.ok().body(fileNames);
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> downloadFiles(@PathVariable("filename") String filename) throws IOException {
        DownloadFileDto downloadFileDto = fileService.downloadFiles(filename);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(Files.probeContentType(downloadFileDto.getFilePath())))
                .headers(downloadFileDto.getHttpHeaders())
                .body(downloadFileDto.getResource());
    }
}
