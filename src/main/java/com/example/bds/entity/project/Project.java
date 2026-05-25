package com.example.bds.entity.project;

import com.example.bds.entity.BasedEntity;
import com.example.bds.entity.organization.Organization;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "projects")
public class Project extends BasedEntity {


    // ================= BASIC =================
    @Enumerated(EnumType.STRING)
    @Column(name = "project_type", nullable = false, length = 50)
    private ProjectType projectType;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "slug", length = 255)
    private String slug;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    // ================= ORGANIZATION =================
    @Column(name = "organization_id")
    private Long organizationId;

    // ================= LOCATION =================
    @Column(name = "province_id")
    private Long provinceId;

    @Column(name = "district_id")
    private Long districtId;

    @Column(name = "ward_id")
    private Long wardId;

    @Column(name = "address_detail", length = 255)
    private String addressDetail;

    @Column(name = "latitude", precision = 10, scale = 7)
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 10, scale = 7)
    private BigDecimal longitude;


    @Column(name = "legal_status", length = 100)
    private String legalStatus;

    @Column(name = "progress_status", length = 100)
    private String progressStatus;

    @OneToOne(mappedBy = "project", orphanRemoval = true)
    private ApartmentProjectDetail apartmentProjectDetail;

    @OneToOne(mappedBy = "project", orphanRemoval = true)
    private ResortProjectDetail resortProjectDetail;

    @OneToOne(mappedBy = "project", orphanRemoval = true)
    private CommercialBuildingProjectDetail commercialBuildingProjectDetail;

    @OneToOne(mappedBy = "project", orphanRemoval = true)
    private UrbanComplexProjectDetail urbanComplexProjectDetail;


    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "organization_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private List<Organization> organizations;


}
