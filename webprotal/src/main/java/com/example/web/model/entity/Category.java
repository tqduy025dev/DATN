package com.example.web.model.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long category_id;

    @Column(name = "category_name", unique = true)
    private String category_name;

    @CreationTimestamp
    @Column(name = "createddate")
    private Timestamp createddate;

    @ManyToMany(mappedBy = "fCategory")
    private Set<Product> fProduct;



}
