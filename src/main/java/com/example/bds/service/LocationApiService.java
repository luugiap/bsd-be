package com.example.bds.service;

import com.example.bds.dto.external.LocationDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;

/**
 * Service lấy thông tin tỉnh/huyện/xã từ external API (provinces.open-api.vn)
 * kết hợp cache Redis để giảm số lần gọi API bên ngoài.
 *
 * Flow:
 *   1. Kiểm tra Redis cache
 *   2. Cache hit  → trả về ngay
 *   3. Cache miss → gọi API → lưu vào Redis (TTL 24h) → trả về
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LocationApiService {

    private static final String CACHE_PROVINCE = "location:province:";
    private static final String CACHE_DISTRICT = "location:district:";
    private static final String CACHE_WARD     = "location:ward:";
    private static final Duration CACHE_TTL    = Duration.ofHours(24);

    private final RestTemplate                   restTemplate;
    private final RedisTemplate<String, Object>  redisTemplate;
    private final ObjectMapper                   objectMapper;

    @Value("${location.api.base-url}")
    private String baseUrl;

    // ---------------------------------------------------------------
    //  Public methods
    // ---------------------------------------------------------------

    /**
     * Lấy thông tin tỉnh/thành theo code.
     * @throws ResponseStatusException 400 nếu code không tồn tại
     */
    public LocationDto.ProvinceDto getProvince(String code) {
        String cacheKey = CACHE_PROVINCE + code;
        Object cached = redisTemplate.opsForValue().get(cacheKey);

        if (cached != null) {
            log.debug("[Location cache hit] province:{}", code);
            return convert(cached, LocationDto.ProvinceDto.class);
        }

        log.debug("[Location cache miss] Gọi API province:{}", code);
        LocationDto.ProvinceDto dto = fetchFromApi(
                baseUrl + "/p/" + code,
                LocationDto.ProvinceDto.class,
                "Không tìm thấy tỉnh/thành phố với mã: " + code
        );

        redisTemplate.opsForValue().set(cacheKey, dto, CACHE_TTL);
        return dto;
    }

    /**
     * Lấy thông tin quận/huyện theo code.
     * @throws ResponseStatusException 400 nếu code không tồn tại
     */
    public LocationDto.DistrictDto getDistrict(String code) {
        String cacheKey = CACHE_DISTRICT + code;
        Object cached = redisTemplate.opsForValue().get(cacheKey);

        if (cached != null) {
            log.debug("[Location cache hit] district:{}", code);
            return convert(cached, LocationDto.DistrictDto.class);
        }

        log.debug("[Location cache miss] Gọi API district:{}", code);
        LocationDto.DistrictDto dto = fetchFromApi(
                baseUrl + "/d/" + code,
                LocationDto.DistrictDto.class,
                "Không tìm thấy quận/huyện với mã: " + code
        );

        redisTemplate.opsForValue().set(cacheKey, dto, CACHE_TTL);
        return dto;
    }

    /**
     * Lấy thông tin phường/xã theo code.
     * @throws ResponseStatusException 400 nếu code không tồn tại
     */
    public LocationDto.WardDto getWard(String code) {
        String cacheKey = CACHE_WARD + code;
        Object cached = redisTemplate.opsForValue().get(cacheKey);

        if (cached != null) {
            log.debug("[Location cache hit] ward:{}", code);
            return convert(cached, LocationDto.WardDto.class);
        }

        log.debug("[Location cache miss] Gọi API ward:{}", code);
        LocationDto.WardDto dto = fetchFromApi(
                baseUrl + "/w/" + code,
                LocationDto.WardDto.class,
                "Không tìm thấy phường/xã với mã: " + code
        );

        redisTemplate.opsForValue().set(cacheKey, dto, CACHE_TTL);
        return dto;
    }

    // ---------------------------------------------------------------
    //  Private helpers
    // ---------------------------------------------------------------

    private <T> T fetchFromApi(String url, Class<T> type, String errorMessage) {
        try {
            T result = restTemplate.getForObject(url, type);
            if (result == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage);
            }
            return result;
        } catch (HttpClientErrorException.NotFound ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage);
        } catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Lỗi khi gọi Location API [{}]: {}", url, ex.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.SERVICE_UNAVAILABLE,
                    "Dịch vụ tra cứu địa chỉ tạm thời không khả dụng, vui lòng thử lại sau."
            );
        }
    }

    /**
     * Convert object từ Redis (có thể là LinkedHashMap khi deserialize JSON)
     * về đúng type cần thiết.
     */
    private <T> T convert(Object cached, Class<T> type) {
        return objectMapper.convertValue(cached, type);
    }
}
