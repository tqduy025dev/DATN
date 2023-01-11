package com.server.datn.server.repositories;

import com.server.datn.server.entity.company.Office;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfficeRepository extends JpaRepository<Office, String> {
    Page<Office> findOfficesByOfficeNameContains(String name, Pageable pageable);
}
