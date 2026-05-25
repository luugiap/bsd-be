package com.example.bds.entity.rbac;

import com.example.bds.entity.BasedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Permission extends BasedEntity {


    @Column( nullable = false, name = "resource")
    private String resource;

    @Column(name = "action")
    private String action;

    @Column(name = "code")
    private String code;

    @Column(name ="description")
    private String description;
}
