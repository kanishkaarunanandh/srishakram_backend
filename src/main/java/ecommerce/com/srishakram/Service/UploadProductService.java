package ecommerce.com.srishakram.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UploadProductService {
    @Value("${aws.region}")
    private String region;

    @Value("${aws.s3.bucket}")
    private String bucket;


    private S3Client getS3Client() {
        return S3Client.builder()
                .region(Region.of(region))
                .build();
    }


    // Upload file
    public String uploadFile(MultipartFile file) throws IOException {
        String key = "products/" + UUID.randomUUID() + "_" + file.getOriginalFilename();

        getS3Client().putObject(
                PutObjectRequest.builder()
                        .bucket(bucket)
                        .key(key)
                        .contentType(file.getContentType())
                        .build(),
                RequestBody.fromBytes(file.getBytes())
        );

        return "https://issimg.s3.ap-south-1.amazonaws.com/" + key;
    }
    public List<String> uploadMultipleFiles(List<MultipartFile> files) throws IOException {
        List<String> urls = new ArrayList<>();

        for (MultipartFile file : files) {
            String key = "products/" + UUID.randomUUID() + "_" + file.getOriginalFilename();

            getS3Client().putObject(
                    PutObjectRequest.builder()
                            .bucket(bucket)
                            .key(key)
                            .contentType(file.getContentType())
                            .build(),
                    RequestBody.fromBytes(file.getBytes())
            );

            urls.add("https://issimg.s3.ap-south-1.amazonaws.com/" + key);
        }
        return urls;
    }

    public byte[] downloadFile(String key) throws IOException {
        Path tempFile = Files.createTempFile("s3file-", ".tmp");

        getS3Client().getObject(
                GetObjectRequest.builder()
                        .bucket(bucket)
                        .key(key)
                        .build(),
                tempFile
        );

        return Files.readAllBytes(tempFile);
    }

}
