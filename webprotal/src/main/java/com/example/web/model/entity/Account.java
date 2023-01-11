package com.example.web.model.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "account")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long user_id;

    @Column(name = "user_name", unique = true)
    private String user_name;

    @Column(name = "password")
    private String password;

    @CreationTimestamp
    @Column(name = "createddate")
    private Timestamp createddate;
}
