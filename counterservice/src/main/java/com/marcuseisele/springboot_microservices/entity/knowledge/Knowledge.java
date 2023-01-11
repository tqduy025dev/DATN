package com.server.datn.server.entity.knowledge;

import com.server.datn.server.common.utils.KeyGenarator;
import com.server.datn.server.common.utils.TimeUtils;
import com.server.datn.server.entity.files.SystemFile;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Knowledge {
    @Id
    private String knowledgeId;
    private String topic;
    private String description;
    private String status;
    @Lob
    private String content;
    @OneToOne(cascade = CascadeType.ALL)
    private SystemFile thumbnail;
    private Timestamp createTime;
    @ManyToOne
    @JoinColumn(name = "knowledge_category_id")
    private KnowledgeCategory knowledgeCategory;

    public Knowledge() {
        this.knowledgeId = KeyGenarator.getKey();
        this.createTime = TimeUtils.getTimestampNow();
    }

    public String getKnowledgeId() {
        return knowledgeId;
    }


    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public SystemFile getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(SystemFile thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public void setKnowledgeId(String knowledgeId) {
        this.knowledgeId = knowledgeId;
    }

    public KnowledgeCategory getKnowledgeCategory() {
        return knowledgeCategory;
    }

    public void setKnowledgeCategory(KnowledgeCategory knowledgeCategory) {
        this.knowledgeCategory = knowledgeCategory;
    }
}
