package site.thanhtungle.fileuploaddownload.model.dto;

import lombok.*;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DownloadFileDto {
    private InputStreamResource inputStreamResource;
    private HttpHeaders httpHeaders;
}
