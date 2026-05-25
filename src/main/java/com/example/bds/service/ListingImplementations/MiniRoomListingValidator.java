package com.example.bds.service.ListingImplementations;

import com.example.bds.config.exception.AppException;
import com.example.bds.config.exception.ErrorCode;
import com.example.bds.dto.Request.Listing.MiniRoomListingRequest;
import com.example.bds.entity.listing.PropertyTypeGroup;
import com.example.bds.service.interfaces.Listing.ListingTypeValidator;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Validator cho nhóm MINI_ROOM: MOTEL_ROOM
 */
@Service
public class MiniRoomListingValidator implements ListingTypeValidator<MiniRoomListingRequest> {

    private static final Set<String> ALLOWED_EXTRA_KEYS = Set.of(
            "pet_allowed",       // cho phép nuôi thú cưng
            "gender_policy",     // chính sách giới tính (NAM/NU/TAT_CA)
            "internet_included"  // phí internet đã bao gồm
    );

    @Override
    public PropertyTypeGroup supports() {
        return PropertyTypeGroup.MINI_ROOM;
    }

    @Override
    public void validate(MiniRoomListingRequest request) {
        // 1. Kiểm tra nhóm
        if (PropertyTypeGroup.from(request.getPropertyType()) != PropertyTypeGroup.MINI_ROOM) {
            throw new AppException(ErrorCode.VALIDATION_ERROR,
                    "PropertyType '" + request.getPropertyType() + "' không hợp lệ cho MiniRoom listing");
        }

        // 2. businessType nhà trọ thường chỉ cho RENT
        if (request.getBusinessType() != null &&
                request.getBusinessType().name().equals("SALE")) {
            throw new AppException(ErrorCode.VALIDATION_ERROR,
                    "Nhà trọ/phòng trọ chỉ hỗ trợ hình thức cho thuê (RENT)");
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
