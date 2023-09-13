package site.thanhtungle.fileuploaddownload.model.dto;

import lombok.*;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;

import java.nio.file.Path;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DownloadFileDto {
    private Resource resource;
    private HttpHeaders httpHeaders;
    private Path filePath;
}
