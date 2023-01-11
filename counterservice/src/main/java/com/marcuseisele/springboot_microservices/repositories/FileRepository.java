package com.server.datn.server.repositories;

import com.server.datn.server.entity.files.SystemFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<SystemFile, String> {
}
