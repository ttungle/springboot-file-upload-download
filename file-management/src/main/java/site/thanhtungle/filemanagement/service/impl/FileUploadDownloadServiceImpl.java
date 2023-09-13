package site.thanhtungle.filemanagement.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import site.thanhtungle.filemanagement.service.FileUploadDownloadService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileUploadDownloadServiceImpl implements FileUploadDownloadService {

    @Value("${file.storage.location}")
    private String fileStorageLocation;

    @Override
    public String fileUpload(MultipartFile file) {
        String messageResponse = null;

        if (file.isEmpty()) {
            return "Please select a valid file to upload.";
        }

        try {

            // Get the file and save it
            byte[] bytes = file.getBytes();
            Path path = Paths.get(fileStorageLocation + file.getOriginalFilename());
            Files.write(path, bytes);

            messageResponse = "You successfully uploaded '" + file.getOriginalFilename() + "'";

        } catch (IOException e) {
            e.printStackTrace();
        }

        return messageResponse;
    }

    @Override
    public InputStreamResource downloadFile(String fileName) {
        InputStreamResource resource = null;

        try {
            File file = new File(fileStorageLocation + fileName);
            resource = new InputStreamResource(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return resource;
    }
}
