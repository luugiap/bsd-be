package com.example.bds.entity.rbac;

import com.example.bds.entity.BasedEntity;
import com.example.bds.entity.BlacklistToken;
import com.example.bds.entity.listing.Listing;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "user")
public class Users extends BasedEntity {

    @Column(unique = true, nullable = false, length = 50, name = "username")
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(unique = true, nullable = false, length = 100, name ="email")
    private String email;

    @Column(name = "full_name", length = 100)
    private String fullName;

    @Column(length = 15, name = "phone")
    private String phone;

    @Column(name = "image_url")
    private String imageUrl;


    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "account_status", nullable = false, length = 20)
    private AccountStatus status = AccountStatus.ACTIVE;

    @Builder.Default
    @Column(name = "is_verified", nullable = false)
    private Boolean verified = false;

    @Column(name ="age")
    private Integer age;

    public enum AccountStatus {
        PENDING,
        ACTIVE,
        LOCKED,
        BANNED
    }



    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Roles> roles;


    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<Listing> listings = new java.util.ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<BlacklistToken> blacklistTokens = new java.util.ArrayList<>();
}
