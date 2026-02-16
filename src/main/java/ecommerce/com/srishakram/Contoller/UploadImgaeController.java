package ecommerce.com.srishakram.Contoller;

import ecommerce.com.srishakram.Service.UploadImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/files")
public class UploadImgaeController {
     @Autowired
    private  UploadImageService UploadImg;

    public UploadImgaeController(UploadImageService uploadImg) {
        UploadImg = uploadImg;
    }


    // Upload file
    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadFiles(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam("indexes") List<Integer> indexes) throws IOException {

        Map<String, String> result = new HashMap<>();

        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            Integer index = indexes.get(i);
            String url = UploadImg.uploadFile(file);
            result.put(index.toString(), url); // ✅ index → url mapping
        }

        return ResponseEntity.ok(result);
    }


    @PostMapping("/upload-single")
    public ResponseEntity<String> uploadSingle(
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        String url = UploadImg.uploadFile(file);
        return ResponseEntity.ok(url);
    }





    // Download file
    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadFile(@RequestParam String key) throws Exception {
        byte[] fileData = UploadImg.downloadFile(key);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + key)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(fileData);
    }


}

