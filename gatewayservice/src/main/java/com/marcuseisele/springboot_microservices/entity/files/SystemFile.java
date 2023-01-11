package com.server.datn.server.entity.files;

import com.server.datn.server.common.utils.KeyGenarator;
import com.server.datn.server.common.utils.TimeUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class SystemFile {
    @Id
    private String fileId;
    @Column(nullable = false)
    private String data;
    private Timestamp createdTime;
    private String type;
    private String createBy;

    public SystemFile() {
        this.fileId = KeyGenarator.getKey();
        this.createdTime = TimeUtils.getTimestampNow();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFileId() {
        return fileId;
    }


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createTime) {
        this.createdTime = createTime;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
}
