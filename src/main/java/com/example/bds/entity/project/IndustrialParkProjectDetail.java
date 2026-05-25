package com.example.bds.entity.project;

import com.example.bds.entity.BasedEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// be used for INDUSTRIAL_PARK_PROJECT
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "industrial_park_detail")
public class IndustrialParkProjectDetail  extends BasedEntity {

    @Column(name = "total_area")
    private Double totalArea;

    @Column(name = "factory_count")
    private Integer factoryCount;

    @Column(name = "power_supply", length = 100)
    private String powerSupply;

    @Column(name = "water_supply", length = 100)
    private String waterSupply;

    @Column(name = "waste_treatment", length = 255)
    private String wasteTreatment;

    @Column(name = "road_width")
    private Double roadWidth;

    @Column(name = "has_fire_system")
    private Boolean hasFireSystem;

    @Column(name = "industries_allowed", columnDefinition = "TEXT")
    private String industriesAllowed;

    @OneToOne
    @JoinColumn(
            name = "project_id",
            nullable = false
    )
    private Project project;
}
