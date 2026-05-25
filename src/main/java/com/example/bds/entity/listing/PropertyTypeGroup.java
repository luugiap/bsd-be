package com.example.bds.entity.listing;

/**
 * Maps PropertyType (fine-grained) to a group that determines
 * which Detail table is populated for a Listing.
 */
public enum
PropertyTypeGroup {

    APARTMENT,    // APARTMENT, MINI_APARTMENT_SERVICE, CONDOTEL
    HOUSE,        // PRIVATE_HOUSE, VILLA_TOWNHOUSE, STREET_HOUSE
    LAND,         // PROJECT_LAND, LAND, FARM_RESORT_LAND
    COMMERCIAL,   // OFFICE, KIOSK_SHOP, WAREHOUSE_FACTORY
    MINI_ROOM,    // MOTEL_ROOM
    OTHER;        // Fallback – chỉ dùng extra_data JSON trong Listing

    public static PropertyTypeGroup from(PropertyType type) {
        return switch (type) {
            case APARTMENT, MINI_APARTMENT_SERVICE, CONDOTEL      -> APARTMENT;
            case PRIVATE_HOUSE, VILLA_TOWNHOUSE, STREET_HOUSE     -> HOUSE;
            case PROJECT_LAND, LAND, FARM_RESORT_LAND             -> LAND;
            case OFFICE, KIOSK_SHOP, WAREHOUSE_FACTORY            -> COMMERCIAL;
            case MOTEL_ROOM                                        -> MINI_ROOM;
            default                                                -> OTHER;
        };
    }
}
