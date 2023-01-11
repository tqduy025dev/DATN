package com.server.datn.server.repositories;

import com.server.datn.server.entity.job.JobTitle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobTitleRepository extends JpaRepository<JobTitle, String> {
}
