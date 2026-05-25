package com.example.bds.entity.project;

import com.example.bds.entity.BasedEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//be used for
//APARTMENT_PROJECT
//SOCIAL_HOUSING_PROJECT
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "apartment_project_details")
public class ApartmentProjectDetail extends BasedEntity{


    @Column(name = "total_blocks")
    private Integer totalBlocks;

    @Column(name = "total_units")
    private Integer totalUnits;

    @Column(name = "floors")
    private Integer floors;

    @Column(name = "handover_year")
    private Integer handoverYear;

    @OneToOne
    @JoinColumn(
            name = "project_id",
            nullable = false
    )
    private Project project;
}