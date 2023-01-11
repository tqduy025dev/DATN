package com.server.datn.server.common.dto.employee;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.server.datn.server.common.dto.base.FileResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class KnowledgeRequest {
    private String knowledgeId;
    private String knowledgeCategoryId;
    private String description;
    private String name;
    private String status;
    private String topic;
    private String content;
    private String categoryId;
    private String createTime;
    private MultipartFile image;
    private FileResponse thumbnail;
    private List<KnowledgeRequest> knowledges;
    private KnowledgeRequest category;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public FileResponse getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(FileResponse thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getKnowledgeId() {
        return knowledgeId;
    }

    public void setKnowledgeId(String knowledgeId) {
        this.knowledgeId = knowledgeId;
    }

    public String getKnowledgeCategoryId() {
        return knowledgeCategoryId;
    }

    public void setKnowledgeCategoryId(String knowledgeCategoryId) {
        this.knowledgeCategoryId = knowledgeCategoryId;
    }

    public List<KnowledgeRequest> getKnowledges() {
        return knowledges;
    }

    public void setKnowledges(List<KnowledgeRequest> knowledges) {
        this.knowledges = knowledges;
    }

    public KnowledgeRequest getCategory() {
        return category;
    }

    public void setCategory(KnowledgeRequest category) {
        this.category = category;
    }
}
