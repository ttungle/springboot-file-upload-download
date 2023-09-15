package site.thanhtungle.fileuploaddownload.controller;

import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.thanhtungle.fileuploaddownload.model.dto.DownloadFileDto;
import site.thanhtungle.fileuploaddownload.service.FileService;

import java.util.List;

@RestController
@RequestMapping("/files")
@AllArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("/uploads")
    public ResponseEntity<List<String>> uploadFiles(@RequestParam("files")List<MultipartFile> multipartFiles) throws Exception {
        List<String> fileUUID = fileService.uploadFiles(multipartFiles);
        return ResponseEntity.ok().body(fileUUID);
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<InputStreamResource> downloadFiles(@PathVariable("fileName") String fileName) throws Exception {
        DownloadFileDto downloadFileDto = fileService.downloadFiles(fileName);
        return ResponseEntity.ok()
                .headers(downloadFileDto.getHttpHeaders())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(downloadFileDto.getInputStreamResource());
    }
}
