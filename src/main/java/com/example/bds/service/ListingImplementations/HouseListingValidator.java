package com.example.bds.service.ListingImplementations;

import com.example.bds.config.exception.AppException;
import com.example.bds.config.exception.ErrorCode;
import com.example.bds.dto.Request.Listing.HouseListingRequest;
import com.example.bds.entity.listing.PropertyTypeGroup;
import com.example.bds.service.interfaces.Listing.ListingTypeValidator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Set;

/**
 * Validator cho nhóm HOUSE: PRIVATE_HOUSE, VILLA_TOWNHOUSE, STREET_HOUSE
 */
@Service
public class HouseListingValidator implements ListingTypeValidator<HouseListingRequest> {

    private static final Set<String> ALLOWED_EXTRA_KEYS = Set.of(
            "renovation_year",   // năm cải tạo
            "house_number",      // số nhà
            "alley_width"        // chiều rộng hẻm
    );

    @Override
    public PropertyTypeGroup supports() {
        return PropertyTypeGroup.HOUSE;
    }

    @Override
    public void validate(HouseListingRequest request) {
        // 1. Kiểm tra nhóm
        if (PropertyTypeGroup.from(request.getPropertyType()) != PropertyTypeGroup.HOUSE) {
            throw new AppException(ErrorCode.VALIDATION_ERROR,
                    "PropertyType '" + request.getPropertyType() + "' không hợp lệ cho House listing");
        }

        // 2. Diện tích phải hợp lý so với mặt tiền * chiều sâu (nếu cả 3 đều có)
        if (request.getFrontWidth() != null && request.getDepth() != null && request.getArea() != null) {
            BigDecimal landArea = request.getFrontWidth().multiply(request.getDepth());
            // cho phép sai lệch 20% (do đất không vuông, v.v.)
            BigDecimal tolerance = landArea.multiply(BigDecimal.valueOf(1.2));
            if (request.getArea().compareTo(tolerance) > 0) {
                throw new AppException(ErrorCode.VALIDATION_ERROR,
                        "Diện tích khai báo lớn bất thường so với mặt tiền x chiều sâu");
            }
        }

        // 3. Validate extraData
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
