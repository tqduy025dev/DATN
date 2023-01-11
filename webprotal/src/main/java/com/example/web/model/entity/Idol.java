package com.example.web.model.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "idol")
public class Idol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idol_id;

    @Column(name = "idol_name", unique = true)
    private String idol_name;

    @CreationTimestamp
    @Column(name = "createddate")
    private Timestamp createddate;

    @OneToMany(mappedBy = "idol", cascade = CascadeType.ALL)
    private Collection<Product> products;
}
