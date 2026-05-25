package com.example.bds.entity.project;

import com.example.bds.entity.BasedEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


// be used for ECO_RESORT_PROJECT
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "resort_detail")
public class ResortProjectDetail extends BasedEntity {
    @Column(name = "total_area")
    private Double totalArea;

    @Column(name = "villa_count")
    private Integer villaCount;

    @Column(name = "hotel_rooms")
    private Integer hotelRooms;

    @Column(name = "facilities", columnDefinition = "TEXT")
    private String facilities;

    @Column(name = "operation_unit", length = 255)
    private String operationUnit;

    @Column(name = "beach_length")
    private Double beachLength;

    @Column(name = "sea_view")
    private Boolean seaView;

    @OneToOne
    @JoinColumn(
            name = "project_id",
            nullable = false
    )
    private Project project;
}
