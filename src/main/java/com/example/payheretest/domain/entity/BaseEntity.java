package com.example.payheretest.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean deleted;
    private LocalDateTime created_at = LocalDateTime.now();
    private LocalDateTime updated_at;
    private LocalDateTime deleted_at;

    public void delete() {
        this.deleted = true;
        this.deleted_at = LocalDateTime.now();
    }

    public void update() {
        this.updated_at = LocalDateTime.now();
    }
}
