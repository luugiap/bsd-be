package com.example.bds.entity.rbac;

import com.example.bds.entity.BasedEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="role")
public class Roles extends BasedEntity {

    @Column(name="rolenamE")
    private String roleName;

    @Column(name ="description")
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            joinColumns =  @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions;



}
