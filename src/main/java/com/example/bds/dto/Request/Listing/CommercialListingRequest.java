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
 * Request tạo listing cho nhóm COMMERCIAL:
 * OFFICE | KIOSK_SHOP | WAREHOUSE_FACTORY
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommercialListingRequest {

    @NotNull(message = "Property type là bắt buộc")
    private PropertyType propertyType;

    @NotNull(message = "Business type là bắt buộc")
    private BusinessType businessType;

    @NotBlank @Size(max = 255) private String title;
    @NotBlank private String description;

    private List<String> imageUrls;

    @NotNull @DecimalMin(value = "0.0", inclusive = false) private BigDecimal price;
    @NotNull @DecimalMin(value = "0.0", inclusive = false) private BigDecimal area;

    private LegalStatus    legalStatus;
    private InteriorStatus interiorStatus;

    @NotBlank private String provinceCode;
    @NotBlank private String districtCode;
    @NotBlank private String wardCode;
    @Size(max = 255) private String addressDetail;

    // === Commercial-specific fields ===
    @DecimalMin("0.0") private BigDecimal floorArea;
    @Min(1) private Integer floorNumber;
    @Min(1) private Integer totalFloors;

    @DecimalMin("0.0") private BigDecimal frontWidth;
    @DecimalMin("0.0") private BigDecimal roadWidth;
    @DecimalMin("0.0") private BigDecimal ceilingHeight;

    private Boolean hasElevator;
    private Boolean hasParking;
    private Boolean hasLoadingDock;
    private Boolean hasAc;

    @Min(0) private Integer electricityCapacity;    // kW

    private Map<String, Object> extraData;
}
