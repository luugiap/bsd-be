package com.example.bds.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
public class AwsConfig {

    @Value("${aws.secretAccesskey}")
    private String secretAccessKey;

    @Value("${aws.accessKeyId}")
    private String accessKeyId;

    @Value("${aws.region}")
    private Region region;


    @Bean
    public S3Presigner s3Presigner() {
        return S3Presigner.builder()
                .credentialsProvider(StaticCredentialsProvider
                        .create(AwsBasicCredentials.create( accessKeyId, secretAccessKey)))
                .region(region).build();

    }
}
