package com.server.datn.server.services;

import com.server.datn.server.common.dto.employee.KnowledgeRequest;
import com.server.datn.server.entity.knowledge.Knowledge;
import com.server.datn.server.entity.knowledge.KnowledgeCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface KnowledgeService {
    KnowledgeCategory createKnowledgeCategory(KnowledgeRequest knowledgeRequest) throws IOException;
    List<KnowledgeCategory> findAllKnowledgeCategory();
    List<KnowledgeCategory> findKnowledgeCategoryByName(String name);
    Knowledge createKnowledge(KnowledgeRequest knowledgeRequest) throws IOException;
    Page<Knowledge> findAllKnowledge(Pageable pageable);
    Knowledge findKnowledgeById(String id);
    String deleteKnowledgeById(String id);
    String deleteCategoryById(String id);
}
