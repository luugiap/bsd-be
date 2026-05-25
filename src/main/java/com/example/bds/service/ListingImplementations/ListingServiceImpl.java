package com.example.bds.service.ListingImplementations;

import com.example.bds.config.exception.AppException;
import com.example.bds.config.exception.ErrorCode;
import com.example.bds.dto.Request.Listing.*;
import com.example.bds.dto.Response.Listing.ListingResponse;
import com.example.bds.entity.listing.*;
import com.example.bds.entity.listing.detail.*;
import com.example.bds.entity.location.District;
import com.example.bds.entity.location.Province;
import com.example.bds.entity.location.Ward;
import com.example.bds.entity.rbac.Users;
import com.example.bds.repository.*;
import com.example.bds.service.interfaces.Listing.ListingTypeValidator;
import com.example.bds.service.interfaces.Listing.OwnerListingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class ListingServiceImpl implements OwnerListingService {

    private final ListingRepository listingRepository;
    private final ListingValidatorRegistry validatorRegistry;

    // Location repositories
    private final ProvinceRepository provinceRepository;
    private final DistrictRepository districtRepository;
    private final WardRepository     wardRepository;

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
    //  HELPERS
    // =========================================================

    /**
     * Build phần base của Listing (chung cho tất cả loại).
     * Validate địa chỉ và tính pricePerM2.
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

        Province province = provinceRepository.findByCode(provinceCode)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không tìm thấy tỉnh/thành: " + provinceCode));
        District district = districtRepository.findByCode(districtCode)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không tìm thấy quận/huyện: " + districtCode));
        Ward ward = wardRepository.findByCode(wardCode)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không tìm thấy phường/xã: " + wardCode));

        // Kiểm tra tính nhất quán địa chỉ
        if (!district.getProvince().equals(province) || !ward.getDistrict().equals(district)) {
            throw new AppException(ErrorCode.VALIDATION_ERROR, "Địa chỉ không nhất quán (tỉnh/huyện/xã không khớp)");
        }

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
        listing.setProvince(province);
        listing.setDistrict(district);
        listing.setWard(ward);
        listing.setAddressDetail(addressDetail);
        listing.setStatus(ListingStatus.PENDING);

        // Tính price/m2 nếu đủ dữ liệu
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
                .province(listing.getProvince() != null ? listing.getProvince().getCode() : null)
                .district(listing.getDistrict() != null ? listing.getDistrict().getCode() : null)
                .ward(listing.getWard() != null ? listing.getWard().getCode() : null)
                .addressDetail(listing.getAddressDetail())
                .build();
    }
}
