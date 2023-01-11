package com.server.datn.server.services.impl;

import com.server.datn.server.entity.files.SystemFile;
import com.server.datn.server.repositories.FileRepository;
import com.server.datn.server.services.CloudService;
import com.server.datn.server.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;
    private final CloudService cloudService;

    public FileServiceImpl(FileRepository fileRepository, CloudService cloudService) {
        this.fileRepository = fileRepository;
        this.cloudService = cloudService;
    }

    @Override
    public SystemFile createFile(MultipartFile file) throws IOException {
        SystemFile systemFile = new SystemFile();
        String url = cloudService.uploadToCloud(file);
        systemFile.setData(url);
        systemFile.setType(file.getContentType());
        return fileRepository.save(systemFile);
    }

    @Override
    public SystemFile findFileById(String id) throws Exception {
        return fileRepository.findById(id).orElseThrow(Exception::new);
    }


}
