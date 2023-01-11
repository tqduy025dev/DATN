package com.server.datn.server.services;


import com.server.datn.server.entity.files.SystemFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    SystemFile createFile(MultipartFile file) throws IOException;
    SystemFile findFileById(String id) throws Exception;
}
