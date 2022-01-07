package com.example.nacosclient.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author hf
 * date   2022/1/7 15:00
 * description
 */
@EnableConfigurationProperties(FileProperties.class)
@ConfigurationProperties(prefix = "file")
@Configuration
public class FileProperties {

    private String uploadDir;


    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}
