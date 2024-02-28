package com.competa.competademo.service;
import org.springframework.web.multipart.MultipartFile;
/**
 * @author Konstantin Glazunov
 * created on 22-Jan-24 1
 */
public interface FileCheckService {

    boolean isImage(MultipartFile file);

    boolean isFileSizeValid(MultipartFile file, int sizeInMb);
}
