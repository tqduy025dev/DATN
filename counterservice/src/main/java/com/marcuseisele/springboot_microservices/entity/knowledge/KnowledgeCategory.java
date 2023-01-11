package com.server.datn.server.entity.knowledge;

import com.server.datn.server.common.utils.KeyGenarator;
import com.server.datn.server.entity.files.SystemFile;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.List;

@Entity
public class KnowledgeCategory {
    @Id
    private String knowledgeCategoryId;
    private String name;
    private String description;
    private String status;
    @OneToOne(cascade = CascadeType.ALL)
    private SystemFile thumbnail;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "knowledgeCategory")
    @BatchSize(size=50)
    private List<Knowledge> knowledges;

    public KnowledgeCategory() {
        this.knowledgeCategoryId = KeyGenarator.getKey();
    }

    public String getKnowledgeCategoryId() {
        return knowledgeCategoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SystemFile getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(SystemFile thumbnail) {
        this.thumbnail = thumbnail;
    }

    public List<Knowledge> getKnowledges() {
        return knowledges;
    }

    public void setKnowledges(List<Knowledge> knowledges) {
        this.knowledges = knowledges;
    }
}
