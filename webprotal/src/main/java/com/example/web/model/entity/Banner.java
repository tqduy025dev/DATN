package com.example.web.model.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "banner")
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long banner_id;

    @Column(name = "link", columnDefinition = "TEXT")
    private String link;

    @Column(name = "image")
    private String image;

    @Column(name = "status")
    private Boolean status = false;

    @CreationTimestamp
    @Column(name = "createddate")
    private Timestamp createddate;
}
