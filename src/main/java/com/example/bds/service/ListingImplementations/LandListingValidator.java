package com.example.bds.service.ListingImplementations;

import com.example.bds.config.exception.AppException;
import com.example.bds.config.exception.ErrorCode;
import com.example.bds.dto.Request.Listing.LandListingRequest;
import com.example.bds.entity.listing.PropertyTypeGroup;
import com.example.bds.service.interfaces.Listing.ListingTypeValidator;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Validator cho nhóm LAND: PROJECT_LAND, LAND, FARM_RESORT_LAND
 */
@Service
public class LandListingValidator implements ListingTypeValidator<LandListingRequest> {

    private static final Set<String> ALLOWED_EXTRA_KEYS = Set.of(
            "planning_status",       // tình trạng quy hoạch
            "land_certificate_no",   // số sổ đất
            "lot_number"             // số lô
    );

    @Override
    public PropertyTypeGroup supports() {
        return PropertyTypeGroup.LAND;
    }

    @Override
    public void validate(LandListingRequest request) {
        // 1. Kiểm tra nhóm
        if (PropertyTypeGroup.from(request.getPropertyType()) != PropertyTypeGroup.LAND) {
            throw new AppException(ErrorCode.VALIDATION_ERROR,
                    "PropertyType '" + request.getPropertyType() + "' không hợp lệ cho Land listing");
        }

        // 2. Nếu có công trình thì constructionArea phải được khai báo
        if (Boolean.TRUE.equals(request.getHasConstruction()) && request.getConstructionArea() == null) {
            throw new AppException(ErrorCode.VALIDATION_ERROR,
                    "Khi có công trình trên đất, cần khai báo diện tích xây dựng (constructionArea)");
        }

        // 3. constructionArea không được lớn hơn tổng diện tích
        if (request.getConstructionArea() != null && request.getArea() != null) {
            if (request.getConstructionArea().compareTo(request.getArea()) > 0) {
                throw new AppException(ErrorCode.VALIDATION_ERROR,
                        "Diện tích xây dựng không thể lớn hơn tổng diện tích đất");
            }
        }

        // 4. Validate extraData
        if (request.getExtraData() != null) {
            for (String key : request.getExtraData().keySet()) {
                if (!ALLOWED_EXTRA_KEYS.contains(key)) {
                    throw new AppException(ErrorCode.VALIDATION_ERROR,
                            "extraData chứa key không hợp lệ: '" + key + "'");
                }
            }
        }
    }
}
