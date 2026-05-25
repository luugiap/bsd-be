package com.example.bds.service.interfaces;

public interface RateLimiterInterface {
    boolean allowedRequest(String key);

}
