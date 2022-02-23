package com.example.nacosclient.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;

/**
 * @author hf
 * date   2022/2/15 9:16
 * description
 */
public class FileUtil {


    public static void main(String[] args) throws IOException{

//        String srcPath = "E:\\test\\src\\ais_call_record_detail.sql";
//        String destPath = "E:\\test\\dest\\ais_call_record_detail.sql";
        String srcPath = "E:\\test\\src\\openjdk-11+28_windows-x64_bin.zip";
        String destPath = "E:\\test\\dest\\openjdk-11+28_windows-x64_bin.zip";
//        String srcPath = "E:\\test\\src\\Windows_10_64.iso";// 91046
//        String destPath= "E:\\test\\dest\\Windows_10_64.iso"; // 82716
        long start = System.currentTimeMillis();
//        transferTo(srcPath, destPath);// 46891 增加大约0.7G的内存。非共享内存
//        byteBufferCopy(srcPath, destPath); // 72325
//        filesTransferTo(srcPath, destPath);// 56554
//        mapCopy(srcPath, destPath);// 53686 对内存消耗大，大约消耗1.5G的内存。共享内存。
        normalCopy(srcPath, destPath);// 73155 增加大于0.5G的内存。非共享内存
        System.out.println(System.currentTimeMillis() - start);


    }

    /**
     * 文件拷贝推荐使用,
     * @param srcPath
     * @param destPath
     * @throws IOException
     */
    public static void transferTo(String srcPath, String destPath) throws IOException{
        try (RandomAccessFile input = new RandomAccessFile(srcPath, "rw");
             FileChannel inputChannel = input.getChannel();
             FileOutputStream output = new FileOutputStream(destPath);
             FileChannel outputChannel = output.getChannel()) {
            // 源文件大小
            long size = inputChannel.size();
            while (size > 0) {
                // 最大只能处理2G的文件
                long count = inputChannel.transferTo(0, size, outputChannel);
                if (count > 0) {
                    size -= count;
                }
            }
        }
    }

    /**
     * 常规文件拷贝
     * @param srcPath
     * @param destPath
     * @throws IOException
     */
    public static void normalCopy(String srcPath, String destPath) throws IOException{
        try (FileInputStream input = new FileInputStream(srcPath);
             FileOutputStream output = new FileOutputStream(destPath)) {
            byte[] bytes = new byte[1024];
            int n = 0;
            while ((n = input.read(bytes)) != -1) {
                output.write(bytes, 0, n);
            }
        }
    }


    /**
     * 文件拷贝推荐使用
     * @param srcPath
     * @param destPath
     * @throws IOException
     */
    public static void filesTransferTo(String srcPath, String destPath) throws IOException{
        try (FileOutputStream output = new FileOutputStream(destPath)) {
            Files.copy(Paths.get(srcPath), output);
        }
    }

    /**
     * 文件内容读取与修改写入推荐
     * @param srcPath
     * @param destPath
     * @throws IOException
     */
    public static void byteBufferCopy(String srcPath, String destPath) throws IOException{
        try (FileInputStream input = new FileInputStream(srcPath);
             FileChannel inputChannel = input.getChannel();
             FileOutputStream output = new FileOutputStream(destPath);
             FileChannel outputChannel = output.getChannel()) {

            ByteBuffer allocate = ByteBuffer.allocate(1024);
            while (inputChannel.read(allocate) > 0) {
                // 转换
                allocate.flip();

                outputChannel.write(allocate);

                allocate.compact();
            }
        }
    }

    /**
     *
     * @param srcPath
     * @param destPath
     * @throws IOException
     */
    public static void mapCopy(String srcPath, String destPath) throws IOException{
        try (FileChannel inputChannel = (FileChannel.open(Paths.get(srcPath), EnumSet.of(StandardOpenOption.READ)));
             FileChannel outputChannel = (FileChannel.open(Paths.get(destPath), EnumSet.of(StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE)))) {
            long fileSize = inputChannel.size();
            while (fileSize > 0) {
                long size = fileSize > Integer.MAX_VALUE ? Integer.MAX_VALUE : fileSize;
                // 最大2G
                MappedByteBuffer buffer = inputChannel.map(FileChannel.MapMode.READ_ONLY,
                        0, size);
                int write = outputChannel.write(buffer);
                fileSize -= write;
            }
        }

    }

}
