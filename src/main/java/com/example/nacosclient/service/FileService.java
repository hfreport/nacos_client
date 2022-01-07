package com.example.nacosclient.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;
import java.nio.file.Path;

/**
 * @author hf
 * date   2022/1/7 14:58
 * description
 */
public interface FileService {
    /**
     * 存储文件
     * @param file
     * @return
     */
    String storeFile(MultipartFile file);

    /**
     * 加载文件流
     * @param fileName
     * @return
     */
    Resource loadFileAsResource(String fileName);

    /**
     * 拷贝流
     * @param fileName
     * @param outputStream
     * @return
     */
    Path copyStream(String fileName, OutputStream outputStream);

    /**
     * 解析文件路径
     * @param fileName
     * @return
     */
    Path resolvePath(String fileName);

}
