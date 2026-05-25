package com.example.bds.entity;

import com.example.bds.entity.rbac.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "blacklist_token")
@Getter
@Setter
public class BlacklistToken extends BasedEntity {

    @Column(name = "token")
    private String token;

    @ManyToOne
    @JoinColumn(
            name = "user_id"
    )
    private Users user;


}
