package com.example.bds.entity.project;

import com.example.bds.entity.BasedEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//be used for
//NEW_URBAN_AREA_PROJECT
//MIXED_USE_COMPLEX_PROJECT
//VILLA_TOWNHOUSE_PROJECT
//SHOPHOUSE_PROJECT
//STREET_HOUSE_PROJECT


@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "urban_complexdetail")
public class UrbanComplexProjectDetail extends BasedEntity {
    @Column(name = "total_land_area")
    private Double totalLandArea;

    @Column(name = "total_units")
    private Integer totalUnits;

    @Column(name = "green_area_ratio")
    private Double greenAreaRatio;

    @Column(name = "villa_count")
    private Integer villaCount;

    @Column(name = "townhouse_count")
    private Integer townhouseCount;

    @Column(name = "shophouse_count")
    private Integer shophouseCount;

    @Column(name = "infrastructure", length = 255)
    private String infrastructure;

    @Column(name = "internal_facilities", columnDefinition = "TEXT")
    private String internalFacilities;

    @OneToOne
    @JoinColumn(
            name = "project_id",
            nullable = false
    )
    private Project project;
}
