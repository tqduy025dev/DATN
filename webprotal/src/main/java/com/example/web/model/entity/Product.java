package com.example.web.model.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long product_id;

    @Column(name = "product_name", unique = true)
    private String product_name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "link", columnDefinition = "TEXT")
    private String link;

    @Column(name = "view_counter")
    private long view_counter;

    @UpdateTimestamp
    @Column(name = "createddate")
    private Timestamp createddate;

    @Column(name = "image")
    private String image;

    @Column(name = "status_video")
    private String status_video;

    @ManyToOne
    @JoinColumn(name = "idol_id")
    private Idol idol;

    @ManyToMany
    @JoinTable(name = "product_category", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> fCategory;
}
