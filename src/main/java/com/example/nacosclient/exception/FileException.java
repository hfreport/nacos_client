package com.example.nacosclient.exception;

/**
 * @author hf
 * date   2022/1/7 15:13
 * description
 */
public class FileException extends RuntimeException {

    public FileException(String message, Throwable ex) {
        super(message, ex);
    }

    public FileException(String message) {
        super(message);
    }

}
