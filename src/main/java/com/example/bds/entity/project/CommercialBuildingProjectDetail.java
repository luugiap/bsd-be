package com.example.bds.entity.project;

import com.example.bds.entity.BasedEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// OFFICE_BUILDING_PROJECT
//COMMERCIAL_CENTER_PROJECT
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "commercial_building_detail")
public class CommercialBuildingProjectDetail extends BasedEntity {
    @Column(name = "total_floor_area")
    private Double totalFloorArea;

    @Column(name = "floors")
    private Integer floors;

    @Column(name = "basement_levels")
    private Integer basementLevels;

    @Column(name = "office_area")
    private Double officeArea;

    @Column(name = "retail_area")
    private Double retailArea;

    @Column(name = "parking_slots")
    private Integer parkingSlots;

    @Column(name = "building_grade", length = 10)
    private String buildingGrade;

    @OneToOne
    @JoinColumn(
            name = "project_id",
            nullable = false
    )
    private Project project;
}
