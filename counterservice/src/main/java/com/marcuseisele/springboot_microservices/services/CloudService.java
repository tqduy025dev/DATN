package com.server.datn.server.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CloudService {
    String uploadToCloud(MultipartFile file) throws IOException;
    List<String> uploadToCloud(MultipartFile[] files) throws IOException;
}
