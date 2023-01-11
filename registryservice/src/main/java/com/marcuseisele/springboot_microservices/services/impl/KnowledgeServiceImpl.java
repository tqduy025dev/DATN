package com.server.datn.server.services.impl;

import com.server.datn.server.common.dto.employee.KnowledgeRequest;
import com.server.datn.server.common.utils.AppUtils;
import com.server.datn.server.entity.files.SystemFile;
import com.server.datn.server.entity.knowledge.Knowledge;
import com.server.datn.server.entity.knowledge.KnowledgeCategory;
import com.server.datn.server.entity.user.Employee;
import com.server.datn.server.repositories.KnowledgeCategoryRepository;
import com.server.datn.server.repositories.KnowledgeRepository;
import com.server.datn.server.services.CloudService;
import com.server.datn.server.services.KnowledgeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static com.server.datn.server.common.constants.AppConstants.*;

@Service
public class KnowledgeServiceImpl implements KnowledgeService {
    private final KnowledgeCategoryRepository categoryRepository;
    private final KnowledgeRepository knowledgeRepository;
    private final CloudService cloudService;

    public KnowledgeServiceImpl(KnowledgeCategoryRepository categoryRepository, KnowledgeRepository knowledgeRepository, CloudService cloudService) {
        this.categoryRepository = categoryRepository;
        this.knowledgeRepository = knowledgeRepository;
        this.cloudService = cloudService;
    }

    @Override
    public KnowledgeCategory createKnowledgeCategory(KnowledgeRequest knowledgeRequest) throws IOException {
        KnowledgeCategory knowledgeCategory = (KnowledgeCategory) AppUtils.converToEntities(knowledgeRequest, KnowledgeCategory.class);
        SystemFile image = getImage(knowledgeRequest);
        knowledgeCategory.setThumbnail(image);
        return categoryRepository.save(knowledgeCategory);
    }

    @Override
    public List<KnowledgeCategory> findAllKnowledgeCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public List<KnowledgeCategory> findKnowledgeCategoryByName(String name) {
        return categoryRepository.findKnowledgeCategoryByNameContaining(name);
    }

    @Override
    public Knowledge createKnowledge(KnowledgeRequest knowledgeRequest) throws IOException {
        Knowledge knowledge = (Knowledge) AppUtils.converToEntities(knowledgeRequest, Knowledge.class);
        SystemFile image = getImage(knowledgeRequest);
        knowledge.setThumbnail(image);
        KnowledgeCategory knowledgeCategory = categoryRepository.findById(knowledgeRequest.getCategoryId()).orElse(null);
        knowledge.setKnowledgeCategory(knowledgeCategory);
        return knowledgeRepository.save(knowledge);
    }

    @Override
    public Page<Knowledge> findAllKnowledge(Pageable pageable) {
        return knowledgeRepository.findAll(pageable);
    }

    @Override
    public Knowledge findKnowledgeById(String id) {
        return knowledgeRepository.findById(id).orElse(null);
    }

    @Override
    public String deleteKnowledgeById(String id) {
        String status = STATUS_D2;
        boolean exists = knowledgeRepository.existsById(id);
        if (exists) {
            knowledgeRepository.delete(knowledgeRepository.getReferenceById(id));
            status = STATUS_D0;
        }
        return status;
    }

    @Override
    public String deleteCategoryById(String id) {
        String status = STATUS_D2;
        boolean exists = categoryRepository.existsById(id);
        if (exists) {
            categoryRepository.delete(categoryRepository.getReferenceById(id));
            status = STATUS_D0;
        }
        return status;
    }


    private SystemFile getImage(KnowledgeRequest knowledgeRequest) throws IOException {
        String url = cloudService.uploadToCloud(knowledgeRequest.getImage());
        SystemFile image = new SystemFile();
        image.setData(url);
        image.setType(knowledgeRequest.getImage().getContentType());
        image.setCreateBy(AppUtils.getCurrentUserId());
        return image;
    }
}
