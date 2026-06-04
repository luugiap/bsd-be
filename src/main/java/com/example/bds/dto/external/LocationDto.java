package com.example.bds.dto.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTOs ánh xạ response từ provinces.open-api.vn
 *
 * Province: GET /api/p/{code}
 * District: GET /api/d/{code}
 * Ward:     GET /api/w/{code}
 */
public class LocationDto {

    // ===============================================================
    //  Province
    // ===============================================================
    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ProvinceDto {

        /** Mã tỉnh/thành (VD: "01", "79") */
        private String code;

        /** Tên tỉnh/thành (VD: "Hà Nội", "Thành phố Hồ Chí Minh") */
        private String name;

        /** Tên viết tắt không dấu */
        @JsonProperty("codename")
        private String codeName;

        /** Division type: "tỉnh" | "thành phố trung ương" */
        @JsonProperty("division_type")
        private String divisionType;
    }

    // ===============================================================
    //  District
    // ===============================================================
    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DistrictDto {

        /** Mã quận/huyện */
        private String code;

        /** Tên quận/huyện */
        private String name;

        @JsonProperty("codename")
        private String codeName;

        @JsonProperty("division_type")
        private String divisionType;

        /**
         * Mã tỉnh mà quận/huyện này thuộc về.
         * API trả về dưới field "province_code".
         */
        @JsonProperty("province_code")
        private String provinceCode;
    }

    // ===============================================================
    //  Ward
    // ===============================================================
    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WardDto {

        /** Mã phường/xã */
        private String code;

        /** Tên phường/xã */
        private String name;

        @JsonProperty("codename")
        private String codeName;

        @JsonProperty("division_type")
        private String divisionType;

        /**
         * Mã quận/huyện mà phường/xã này thuộc về.
         * API trả về dưới field "district_code".
         */
        @JsonProperty("district_code")
        private String districtCode;
    }
}
