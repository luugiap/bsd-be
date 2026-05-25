package com.example.bds.dto.Request.Listing;

import com.example.bds.entity.listing.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Request tạo listing cho nhóm MINI_ROOM:
 * MOTEL_ROOM (nhà trọ, phòng trọ)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MiniRoomListingRequest {

    @NotNull(message = "Property type là bắt buộc")
    private PropertyType propertyType;

    @NotNull(message = "Business type là bắt buộc")
    private BusinessType businessType;

    @NotBlank @Size(max = 255) private String title;
    @NotBlank private String description;

    private List<String> imageUrls;

    @NotNull @DecimalMin(value = "0.0", inclusive = false) private BigDecimal price;
    @NotNull @DecimalMin(value = "0.0", inclusive = false) private BigDecimal area;

    private InteriorStatus interiorStatus;

    @NotBlank private String provinceCode;
    @NotBlank private String districtCode;
    @NotBlank private String wardCode;
    @Size(max = 255) private String addressDetail;

    // === MiniRoom-specific fields ===
    @Min(0) private Integer totalRoomsInBuilding;

    private Boolean hasPrivateWc;
    private Boolean hasBalcony;
    private Boolean hasAc;
    private Boolean hasWaterHeater;
    private Boolean hasParking;

    @DecimalMin("0.0") private BigDecimal electricityPrice; // VNĐ/kWh
    @DecimalMin("0.0") private BigDecimal waterPrice;       // VNĐ/m³

    private Map<String, Object> extraData;
}
