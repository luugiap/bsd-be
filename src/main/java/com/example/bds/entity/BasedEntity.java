package com.example.bds.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BasedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( name = "created", nullable = false, updatable = false)
    private Timestamp  created;

    @Column(name = "updated", nullable = false)
    private Timestamp  updated;

    @Column(name = "isdeleted")
    private boolean isdeleted = false;

    @PrePersist
    public void prePersist() {
        this.created = new Timestamp(System.currentTimeMillis());
        this.updated = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    public void preUpdate() {
        this.updated = new Timestamp(System.currentTimeMillis());
    }


}
