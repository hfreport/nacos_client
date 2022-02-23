package com.example.nacosclient.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * @author hf
 * date   2022/2/16 10:33
 * description
 */
public class ZipFileUtil {

    public static void main(String[] args) throws IOException{

//        String srcPath = "E:\\test\\src\\ais_call_record_detail.sql";
//        String destPath = "E:\\test\\dest\\ais_call_record_detail.sql";
        String srcPath = "E:\\test\\dest\\openjdk-11+28_windows.zip";
        String destPath = "E:\\test\\dest";
//        String srcPath = "E:\\test\\src\\Windows_10_64.iso";// 91046
//        String destPath = "E:\\test\\dest\\Windows_10_64.iso"; // 82716
        long start = System.currentTimeMillis();
        unZipFile(srcPath, destPath);
//        zipFile("E:\\test\\dest\\jdk-11", "E:\\test\\dest\\openjdk-11+28_windows.zip");
        System.out.println(System.currentTimeMillis() - start);

    }

    /**
     * 解压文件
     * @param srcPath
     * @param destPath
     * @throws IOException
     */
    public static void unZipFile(String srcPath, String destPath) throws IOException {
        ZipFile input = new ZipFile(srcPath);
        Enumeration<? extends ZipEntry> entries = input.entries();
        while (entries.hasMoreElements()) {
            ZipEntry zipEntry = entries.nextElement();
            String target = destPath + "/" + zipEntry.getName();
            File file = new File(target);
            if (zipEntry.isDirectory()) {
                if (!file.exists()) {
                    file.mkdir();
                }
            } else {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdir();
                }
                if (file.exists()) {
                    file.delete();
                }
                // 获取输入流
                InputStream entityStream = input.getInputStream(zipEntry);
                Files.copy(entityStream, Paths.get(target));
            }
        }
    }

    /**
     * 压缩文件
     * @param srcPath
     * @param destPath
     * @throws IOException
     */
    public static void zipFile(String srcPath, String destPath) throws IOException {
        // 构建压缩流
        try (ZipOutputStream output = new ZipOutputStream(new FileOutputStream(destPath))){
            File file = new File(srcPath);
            compress(file, file.getName(), output);
        }
    }

    /**
     * 压缩文件流
     * @param srcFile
     * @param output
     */
    private static void compress(File srcFile, String entryName, ZipOutputStream output) throws IOException {
        if (srcFile.isDirectory()) {
            output.putNextEntry(new ZipEntry(entryName + "/"));
            File[] files = srcFile.listFiles();
            Iterator<File> iterator = Arrays.asList(files).iterator();
            while (iterator.hasNext()) {
                File next = iterator.next();
                compress(next, entryName + "/" + next.getName(), output);
            }
        } else {
            //创建文件
            output.putNextEntry(new ZipEntry(entryName));
            Files.copy(Paths.get(srcFile.getPath()), output);
        }
    }
}
