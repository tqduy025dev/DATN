package com.server.datn.server.repositories;

import com.server.datn.server.entity.manager.LeaveCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveCategoryRepository extends JpaRepository<LeaveCategory, String> {
}
