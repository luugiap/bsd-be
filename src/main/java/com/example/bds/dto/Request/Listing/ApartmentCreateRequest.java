package com.example.bds.dto.Request.Listing;

import com.example.bds.entity.listing.BalconyDirection;
import com.example.bds.entity.listing.LegalStatus;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApartmentCreateRequest {

    @NotNull(message = "Property type is required")
    private Long propertyId;

    @NotNull(message = "category is required")
    private Long categoryId;
    // ================= CONTENT =================

    @NotBlank(message = "Title must not be empty")
    @Size(max = 255, message = "Title must be less than 255 characters")
    private String title;

    @NotBlank(message = "Description must not be empty")
    private String description;

    // ================= IMAGES =================


    private List<String> imageUrls;

    // ================= PRICE =================

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;


    // ================= COMMON ATTRIBUTES =================

    @Min(value = 0, message = "Bedrooms must be >= 0")
    private Integer bedrooms;

    @Min(value = 0, message = "Bathrooms must be >= 0")
    private Integer bathrooms;

    @NotNull(message = "Balcony direction is required")
    private BalconyDirection balconyDirection;

    private Map<String, Object> attributes;

    private LegalStatus legalStatus;




    // ================= LOCATION =================

    @NotBlank(message = "Province  is required")
    private String provinceCode;

    @NotBlank(message = "District  is required")
    private String districtCode;

    @NotBlank(message = "Ward is required")
    private String wardCode;

    @Size(max = 255, message = "Address detail max length is 255")
    private String addressDetail;

    @NotNull(message = "Area is required")
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal area;

    // ================= DYNAMIC =================

}