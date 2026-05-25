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
 * Request tạo listing cho nhóm APARTMENT:
 * APARTMENT | MINI_APARTMENT_SERVICE | CONDOTEL
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApartmentListingRequest {

    // === Loại BĐS ===
    @NotNull(message = "Property type là bắt buộc")
    private PropertyType propertyType;   // phải thuộc nhóm APARTMENT

    @NotNull(message = "Business type là bắt buộc")
    private BusinessType businessType;

    // === Content ===
    @NotBlank(message = "Tiêu đề không được để trống")
    @Size(max = 255, message = "Tiêu đề tối đa 255 ký tự")
    private String title;

    @NotBlank(message = "Mô tả không được để trống")
    private String description;

    private List<String> imageUrls;

    // === Price & Area ===
    @NotNull(message = "Giá là bắt buộc")
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá phải lớn hơn 0")
    private BigDecimal price;

    @NotNull(message = "Diện tích là bắt buộc")
    @DecimalMin(value = "0.0", inclusive = false, message = "Diện tích phải lớn hơn 0")
    private BigDecimal area;

    // === Pháp lý & Nội thất ===
    private LegalStatus legalStatus;
    private InteriorStatus interiorStatus;

    // === Địa chỉ ===
    @NotBlank(message = "Tỉnh/Thành phố là bắt buộc")
    private String provinceCode;

    @NotBlank(message = "Quận/Huyện là bắt buộc")
    private String districtCode;

    @NotBlank(message = "Phường/Xã là bắt buộc")
    private String wardCode;

    @Size(max = 255)
    private String addressDetail;

    // === Apartment-specific fields ===
    @Min(value = 0, message = "Số phòng ngủ phải >= 0")
    @Max(value = 20, message = "Số phòng ngủ tối đa 20")
    private Integer bedrooms;

    @Min(value = 0, message = "Số phòng tắm phải >= 0")
    @Max(value = 20, message = "Số phòng tắm tối đa 20")
    private Integer bathrooms;

    @Min(value = 1, message = "Số tầng phải >= 1")
    private Integer floorNumber;

    @Min(value = 1, message = "Tổng số tầng phải >= 1")
    private Integer totalFloors;

    @Size(max = 100)
    private String blockName;

    @Size(max = 255)
    private String projectName;

    private BalconyDirection balconyDirection;
    private HouseDirection   houseDirection;

    private Boolean hasElevator;
    private Boolean hasPool;
    private Boolean hasGym;
    private Boolean hasParking;

    // === JSON fallback (tùy chọn, key phải nằm trong whitelist) ===
    private Map<String, Object> extraData;
}
