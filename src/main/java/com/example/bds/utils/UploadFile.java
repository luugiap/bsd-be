package com.example.bds.utils;

import com.example.bds.dto.Response.UrlResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class UploadFile {

   private final S3Presigner s3Presigner;

   @Value("${aws.region}")
   private Region region;

   public UrlResponse generateUrl() {
       String key = "images/"+System.currentTimeMillis()+".jpg";
       String bucket = "images";


       PutObjectRequest putObjectRequest = PutObjectRequest.builder()
               .bucket(bucket)
               .key(key)
               .contentType("image/jpeg")
               .build();

       PutObjectPresignRequest putObjectPresignRequest = PutObjectPresignRequest.builder()
               .putObjectRequest(putObjectRequest)
               .signatureDuration(Duration.ofHours(1))
               .build();
       //dùng để upload file
       String urlUpload = s3Presigner.presignPutObject(putObjectPresignRequest)
               .url()
               .toString();
       //dùng để truy cập file sau khi đã upload thành công
       String fileUrl = String.format(
               "https://%s.s3.%s.amazonaws.com/%s",
               bucket,
               region.id(),
               key
       );
       return new UrlResponse(urlUpload,fileUrl);

   }




}
