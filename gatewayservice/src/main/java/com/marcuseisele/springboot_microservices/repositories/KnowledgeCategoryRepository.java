package com.server.datn.server.repositories;

import com.server.datn.server.entity.knowledge.KnowledgeCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KnowledgeCategoryRepository extends JpaRepository<KnowledgeCategory, String> {
    List<KnowledgeCategory> findKnowledgeCategoryByNameContaining(String name);
}
