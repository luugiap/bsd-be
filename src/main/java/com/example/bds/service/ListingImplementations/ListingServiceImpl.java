package com.example.bds.service.ListingImplementations;

import com.example.bds.config.exception.AppException;
import com.example.bds.config.exception.ErrorCode;
import com.example.bds.dto.Request.Listing.*;
import com.example.bds.dto.Response.Listing.ListingResponse;
import com.example.bds.dto.Response.Listing.PageMetadata;
import com.example.bds.dto.Response.Listing.PagedListingResponse;
import com.example.bds.dto.external.LocationDto;
import com.example.bds.entity.listing.*;
import com.example.bds.entity.listing.detail.*;
import com.example.bds.entity.rbac.Users;
import com.example.bds.repository.*;
import com.example.bds.service.LocationApiService;
import com.example.bds.service.interfaces.Listing.ListingTypeValidator;
import com.example.bds.service.interfaces.Listing.OwnerListingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ListingServiceImpl implements OwnerListingService {

    private final ListingRepository listingRepository;
    private final ListingValidatorRegistry validatorRegistry;
    private final LocationApiService locationApiService;

    // =========================================================
    //  CREATE APARTMENT
    // =========================================================
    @Override
    @Transactional
    public ListingResponse createApartment(Users owner, ApartmentListingRequest request) {
        // 1. Business-rule validation
        ListingTypeValidator<ApartmentListingRequest> validator =
                validatorRegistry.getValidator(PropertyTypeGroup.APARTMENT);
        validator.validate(request);

        // 2. Build base listing
        Listing listing = buildBaseListing(owner,
                request.getPropertyType(), request.getBusinessType(),
                request.getTitle(), request.getDescription(),
                request.getPrice(), request.getArea(),
                request.getLegalStatus(), request.getInteriorStatus(),
                request.getProvinceCode(), request.getDistrictCode(), request.getWardCode(),
                request.getAddressDetail());

        // 3. Build detail
        ApartmentDetail detail = new ApartmentDetail();
        detail.setListing(listing);
        detail.setBedrooms(request.getBedrooms());
        detail.setBathrooms(request.getBathrooms());
        detail.setFloorNumber(request.getFloorNumber());
        detail.setTotalFloors(request.getTotalFloors());
        detail.setBlockName(request.getBlockName());
        detail.setProjectName(request.getProjectName());
        detail.setBalconyDirection(request.getBalconyDirection());
        detail.setHouseDirection(request.getHouseDirection());
        detail.setHasElevator(request.getHasElevator());
        detail.setHasPool(request.getHasPool());
        detail.setHasGym(request.getHasGym());
        detail.setHasParking(request.getHasParking());
        detail.setExtraData(request.getExtraData());

        listing.setApartmentDetail(detail);
        listingRepository.save(listing);

        return toResponse(listing);
    }

    // =========================================================
    //  CREATE HOUSE
    // =========================================================
    @Override
    @Transactional
    public ListingResponse createHouse(Users owner, HouseListingRequest request) {
        ListingTypeValidator<HouseListingRequest> validator =
                validatorRegistry.getValidator(PropertyTypeGroup.HOUSE);
        validator.validate(request);

        Listing listing = buildBaseListing(owner,
                request.getPropertyType(), request.getBusinessType(),
                request.getTitle(), request.getDescription(),
                request.getPrice(), request.getArea(),
                request.getLegalStatus(), request.getInteriorStatus(),
                request.getProvinceCode(), request.getDistrictCode(), request.getWardCode(),
                request.getAddressDetail());

        HouseDetail detail = new HouseDetail();
        detail.setListing(listing);
        detail.setBedrooms(request.getBedrooms());
        detail.setBathrooms(request.getBathrooms());
        detail.setFloors(request.getFloors());
        detail.setHouseDirection(request.getHouseDirection());
        detail.setBalconyDirection(request.getBalconyDirection());
        detail.setFrontWidth(request.getFrontWidth());
        detail.setRoadWidth(request.getRoadWidth());
        detail.setDepth(request.getDepth());
        detail.setHasRooftop(request.getHasRooftop());
        detail.setHasBasement(request.getHasBasement());
        detail.setHasGarage(request.getHasGarage());
        detail.setHasGarden(request.getHasGarden());
        detail.setExtraData(request.getExtraData());

        listing.setHouseDetail(detail);
        listingRepository.save(listing);

        return toResponse(listing);
    }

    // =========================================================
    //  CREATE LAND
    // =========================================================
    @Override
    @Transactional
    public ListingResponse createLand(Users owner, LandListingRequest request) {
        ListingTypeValidator<LandListingRequest> validator =
                validatorRegistry.getValidator(PropertyTypeGroup.LAND);
        validator.validate(request);

        Listing listing = buildBaseListing(owner,
                request.getPropertyType(), request.getBusinessType(),
                request.getTitle(), request.getDescription(),
                request.getPrice(), request.getArea(),
                request.getLegalStatus(), null,
                request.getProvinceCode(), request.getDistrictCode(), request.getWardCode(),
                request.getAddressDetail());

        LandDetail detail = new LandDetail();
        detail.setListing(listing);
        detail.setLandType(request.getLandType());
        detail.setLandDirection(request.getLandDirection());
        detail.setFrontWidth(request.getFrontWidth());
        detail.setRoadWidth(request.getRoadWidth());
        detail.setDepth(request.getDepth());
        detail.setIsCornerLot(request.getIsCornerLot());
        detail.setIsNearRoad(request.getIsNearRoad());
        detail.setHasConstruction(request.getHasConstruction());
        detail.setConstructionArea(request.getConstructionArea());
        detail.setExtraData(request.getExtraData());

        listing.setLandDetail(detail);
        listingRepository.save(listing);

        return toResponse(listing);
    }

    // =========================================================
    //  CREATE COMMERCIAL
    // =========================================================
    @Override
    @Transactional
    public ListingResponse createCommercial(Users owner, CommercialListingRequest request) {
        ListingTypeValidator<CommercialListingRequest> validator =
                validatorRegistry.getValidator(PropertyTypeGroup.COMMERCIAL);
        validator.validate(request);

        Listing listing = buildBaseListing(owner,
                request.getPropertyType(), request.getBusinessType(),
                request.getTitle(), request.getDescription(),
                request.getPrice(), request.getArea(),
                request.getLegalStatus(), request.getInteriorStatus(),
                request.getProvinceCode(), request.getDistrictCode(), request.getWardCode(),
                request.getAddressDetail());

        CommercialDetail detail = new CommercialDetail();
        detail.setListing(listing);
        detail.setFloorArea(request.getFloorArea());
        detail.setFloorNumber(request.getFloorNumber());
        detail.setTotalFloors(request.getTotalFloors());
        detail.setFrontWidth(request.getFrontWidth());
        detail.setRoadWidth(request.getRoadWidth());
        detail.setCeilingHeight(request.getCeilingHeight());
        detail.setHasElevator(request.getHasElevator());
        detail.setHasParking(request.getHasParking());
        detail.setHasLoadingDock(request.getHasLoadingDock());
        detail.setHasAc(request.getHasAc());
        detail.setElectricityCapacity(request.getElectricityCapacity());
        detail.setExtraData(request.getExtraData());

        listing.setCommercialDetail(detail);
        listingRepository.save(listing);

        return toResponse(listing);
    }

    // =========================================================
    //  CREATE MINI ROOM
    // =========================================================
    @Override
    @Transactional
    public ListingResponse createMiniRoom(Users owner, MiniRoomListingRequest request) {
        ListingTypeValidator<MiniRoomListingRequest> validator =
                validatorRegistry.getValidator(PropertyTypeGroup.MINI_ROOM);
        validator.validate(request);

        Listing listing = buildBaseListing(owner,
                request.getPropertyType(), request.getBusinessType(),
                request.getTitle(), request.getDescription(),
                request.getPrice(), request.getArea(),
                null, request.getInteriorStatus(),
                request.getProvinceCode(), request.getDistrictCode(), request.getWardCode(),
                request.getAddressDetail());

        MiniRoomDetail detail = new MiniRoomDetail();
        detail.setListing(listing);
        detail.setTotalRoomsInBuilding(request.getTotalRoomsInBuilding());
        detail.setHasPrivateWc(request.getHasPrivateWc());
        detail.setHasBalcony(request.getHasBalcony());
        detail.setHasAc(request.getHasAc());
        detail.setHasWaterHeater(request.getHasWaterHeater());
        detail.setHasParking(request.getHasParking());
        detail.setElectricityPrice(request.getElectricityPrice());
        detail.setWaterPrice(request.getWaterPrice());
        detail.setExtraData(request.getExtraData());

        listing.setMiniRoomDetail(detail);
        listingRepository.save(listing);

        return toResponse(listing);
    }

    // =========================================================
    //  SEARCH WITH FALLBACK
    // =========================================================
    @Override
    public PagedListingResponse search(String text, String provinceCode, String districtCode,
                                       String wardCode, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);

        Page<Listing> result = listingRepository.searchListings(
                text,
                provinceCode == null || provinceCode.isBlank() ? null : provinceCode,
                districtCode == null || districtCode.isBlank() ? null : districtCode,
                wardCode     == null || wardCode.isBlank()     ? null : wardCode,
                pageable
        );

        boolean fallback = result.isEmpty();
        String message;

        if (fallback) {
            result = listingRepository.findAllByStatus(ListingStatus.APPROVED, pageable);
            message = "Không tìm thấy kết quả phù hợp, đây là các bài đăng có thể hữu ích cho bạn";
        } else {
            message = "Tìm kiếm thành công";
        }

        List<ListingResponse> content = result.getContent().stream()
                .map(this::toResponse)
                .toList();

        PageMetadata metadata = new PageMetadata(page, size, result.getTotalElements(), fallback);
        return new PagedListingResponse(200, message, content, metadata);
    }

    // =========================================================
    //  HELPERS
    // =========================================================

    /**
     * Build phần base của Listing (chung cho tất cả loại).
     * Validate địa chỉ qua external API (provinces.open-api.vn) + Redis cache.
     * Tính pricePerM2 nếu đủ dữ liệu.
     */
    private Listing buildBaseListing(
            Users owner,
            PropertyType propertyType,
            BusinessType businessType,
            String title, String description,
            BigDecimal price, BigDecimal area,
            LegalStatus legalStatus, InteriorStatus interiorStatus,
            String provinceCode, String districtCode, String wardCode,
            String addressDetail) {

        // 1. Lấy thông tin địa chỉ từ external API (có cache Redis)
        LocationDto.ProvinceDto province = locationApiService.getProvince(provinceCode);
        LocationDto.DistrictDto district = locationApiService.getDistrict(districtCode);
        LocationDto.WardDto     ward     = locationApiService.getWard(wardCode);

        // 2. Kiểm tra tính nhất quán: district phải thuộc province, ward phải thuộc district
        if (!provinceCode.equals(district.getProvinceCode())) {
            throw new AppException(ErrorCode.VALIDATION_ERROR,
                    "Quận/huyện '" + district.getName() + "' không thuộc tỉnh/thành '" + province.getName() + "'");
        }
        if (!districtCode.equals(ward.getDistrictCode())) {
            throw new AppException(ErrorCode.VALIDATION_ERROR,
                    "Phường/xã '" + ward.getName() + "' không thuộc quận/huyện '" + district.getName() + "'");
        }

        // 3. Build entity
        Listing listing = new Listing();
        listing.setUser(owner);
        listing.setPropertyType(propertyType);
        listing.setBusinessType(businessType);
        listing.setTitle(title);
        listing.setDescription(description);
        listing.setPrice(price);
        listing.setArea(area);
        listing.setLegalStatus(legalStatus);
        listing.setInteriorStatus(interiorStatus);

        // Lưu cả code lẫn tên (không cần FK vào bảng location)
        listing.setProvinceCode(province.getCode());
        listing.setProvinceName(province.getName());
        listing.setDistrictCode(district.getCode());
        listing.setDistrictName(district.getName());
        listing.setWardCode(ward.getCode());
        listing.setWardName(ward.getName());
        listing.setAddressDetail(addressDetail);
        listing.setStatus(ListingStatus.PENDING);

        // 4. Tính price/m2 nếu đủ dữ liệu
        if (price != null && area != null && area.compareTo(BigDecimal.ZERO) > 0) {
            listing.setPricePerM2(price.divide(area, 2, RoundingMode.HALF_UP));
        }

        return listing;
    }

    private ListingResponse toResponse(Listing listing) {
        return ListingResponse.builder()
                .id(listing.getId())
                .title(listing.getTitle())
                .description(listing.getDescription())
                .propertyType(listing.getPropertyType())
                .businessType(listing.getBusinessType())
                .status(listing.getStatus())
                .price(listing.getPrice())
                .area(listing.getArea())
                .pricePerM2(listing.getPricePerM2())
                .provinceCode(listing.getProvinceCode())
                .provinceName(listing.getProvinceName())
                .districtCode(listing.getDistrictCode())
                .districtName(listing.getDistrictName())
                .wardCode(listing.getWardCode())
                .wardName(listing.getWardName())
                .addressDetail(listing.getAddressDetail())
                .build();
    }
}
