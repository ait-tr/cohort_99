package com.competa.competademo.service.impl;
import com.competa.competademo.service.FileCheckService;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
/**
 * @author Konstantin Glazunov
 * created on 22-Jan-24 1
 */
@Service
public class FileCheckServiceImpl implements FileCheckService {

    @Override
    public boolean isImage(MultipartFile file) {
        try {
            Tika tika = new Tika();
            String fileType = tika.detect(file.getInputStream());
            return fileType.startsWith("image/");
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean isFileSizeValid(MultipartFile file, int sizeInMb) {
        return file.getSize() <= sizeInMb * 1024 * 1024;
    }
}
