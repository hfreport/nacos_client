package com.example.nacosclient.service.impl;

import com.example.nacosclient.config.FileProperties;
import com.example.nacosclient.exception.FileException;
import com.example.nacosclient.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @author hf
 * date   2022/1/7 14:58
 * description
 */
@Service
@Slf4j
public class FileServiceImpl implements FileService {

    /**
     * 文件在本地存储的地址
     */
    private final Path fileStorageLocation;

    @Autowired
    public FileServiceImpl(FileProperties fileProperties) throws FileNotFoundException {
        this.fileStorageLocation = Paths.get(fileProperties.getUploadDir()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileNotFoundException("Could not create the directory where the uploaded files will be stored.");
        }
    }

    @Override
    public Path resolvePath(String fileName) {
        return this.fileStorageLocation.resolve(fileName).normalize();
    }

    /**
     * 存储文件到系统
     * @param file 文件
     * @return 文件名
     */
    @Override
    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if(fileName.contains("..")) {
                throw new FileException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException ex) {
            throw new FileException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    /**
     * 加载文件
     * @param fileName 文件名
     * @return 文件
     */
    @Override
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new FileException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileException("File not found " + fileName, ex);
        }
    }

    @Override
    public Path copyStream(String fileName, OutputStream outputStream) {
        Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
        try {
            Files.copy(filePath, outputStream);
        } catch (IOException e) {
            throw new FileException("File not found " + fileName);
        }
        return filePath;
    }
}
