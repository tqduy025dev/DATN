package com.server.datn.server.repositories;

import com.server.datn.server.entity.job.JobLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JobLevelRepository extends JpaRepository<JobLevel, String> {
    @Query(value = "SELECT MAX(level.level) + 1 FROM job_level level", nativeQuery = true)
    Integer findJobLevelByMaxLevel();
    Page<JobLevel> findAllByOrderByLevelDesc(Pageable pageable);
}
