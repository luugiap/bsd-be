package com.example.bds.entity.listing.detail;

import com.example.bds.entity.BasedEntity;
import com.example.bds.entity.listing.Listing;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Detail bảng cho nhóm: MOTEL_ROOM (nhà trọ, phòng trọ)
 */
@Entity
@Table(name = "listing_mini_room_detail")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MiniRoomDetail extends BasedEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "listing_id", unique = true, nullable = false)
    private Listing listing;

    // === Phòng ===
    @Column(name = "total_rooms_in_building")
    private Integer totalRoomsInBuilding; // tổng số phòng trong tòa

    // === Tiện ích phòng ===
    @Column(name = "has_private_wc")
    private Boolean hasPrivateWc;       // WC riêng hay chung

    @Column(name = "has_balcony")
    private Boolean hasBalcony;

    @Column(name = "has_ac")
    private Boolean hasAc;

    @Column(name = "has_water_heater")
    private Boolean hasWaterHeater;

    @Column(name = "has_parking")
    private Boolean hasParking;

    // === Chi phí ===
    @Column(name = "electricity_price", precision = 10, scale = 2)
    private BigDecimal electricityPrice; // VNĐ/kWh

    @Column(name = "water_price", precision = 10, scale = 2)
    private BigDecimal waterPrice;       // VNĐ/m³

    // === JSON fallback ===
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "extra_data", columnDefinition = "JSON")
    private Map<String, Object> extraData;
}
