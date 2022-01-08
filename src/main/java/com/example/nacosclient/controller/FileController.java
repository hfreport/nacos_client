package com.example.nacosclient.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.nacosclient.exception.FileException;
import com.example.nacosclient.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Path;

/**
 * @author hf
 * date   2022/1/7 14:15
 * description
 */
@RestController
@RequestMapping(value = "/file")
@Slf4j
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam MultipartFile[] files) {
        JSONObject object=new JSONObject();
        for(int i=0;i<files.length;i++){
            try {
                fileService.storeFile(files[i]);
            } catch (Exception e) {
                log.error("{}",e);
                object.put("success",2);
                object.put("result","程序错误，请重新上传");
                return object.toString();
            }
        }
        object.put("success",1);
        object.put("result","文件上传成功");
        return object.toString();
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileService.loadFileAsResource(fileName);
        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            log.info("Could not determine file type.");
        }
        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    /**
     * @param response
     * @param fileName
     */
    @RequestMapping("/download")
    public void fileDownLoad(HttpServletResponse response, @RequestParam("fileName") String fileName){
        Path path = fileService.resolvePath(fileName);
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) path.toFile().length());
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName );
        try {
            fileService.copyStream(fileName, response.getOutputStream());
        } catch (IOException e) {
            log.error("{}",e);
            throw new FileException(e);
        }
    }

}
