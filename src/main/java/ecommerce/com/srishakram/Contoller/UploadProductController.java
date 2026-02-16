package ecommerce.com.srishakram.Contoller;

import ecommerce.com.srishakram.Service.UploadProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/files/products")
public class UploadProductController {

    @Autowired
    private UploadProductService uploadProImg;

    @PostMapping("/upload")
    public ResponseEntity<List<String>> uploadMultipleFiles(@RequestParam("files") List<MultipartFile> files) throws IOException {
        List<String> keys = new ArrayList<>();
        for (MultipartFile file : files) {
            keys.add(uploadProImg.uploadFile(file));
        }
        return ResponseEntity.ok(keys);
    }

    @PostMapping("/upload/single")
    public ResponseEntity<String> uploadMultipleFiles(@RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(uploadProImg.uploadFile(file));
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadFile(@RequestParam String key) throws Exception {
        byte[] fileData = uploadProImg.downloadFile(key);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + key)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(fileData);
    }
}
