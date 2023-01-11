package com.server.datn.server.repositories;

import com.server.datn.server.entity.knowledge.Knowledge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KnowledgeRepository extends JpaRepository<Knowledge, String> {
}
