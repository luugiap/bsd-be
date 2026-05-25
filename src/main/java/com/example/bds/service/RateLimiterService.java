package com.example.bds.service;

import com.example.bds.config.rateLimit.BucketTokenConfig;
import com.example.bds.config.rateLimit.InMemoryTokenBucket;
import com.example.bds.config.rateLimit.TokenBucket;
import com.example.bds.service.interfaces.RateLimiterInterface;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class RateLimiterService implements RateLimiterInterface {

    private final ConcurrentHashMap<String, TokenBucket > buckets = new ConcurrentHashMap<>();
    private final BucketTokenConfig bucketTokenConfig;
   
    @Override
    public boolean allowedRequest(String key) {
          TokenBucket tokenBucket = buckets.computeIfAbsent(key,k-> new InMemoryTokenBucket(
                  bucketTokenConfig.getCapacity(),
                  bucketTokenConfig.getRefillBucket(),
                  bucketTokenConfig.getRateFillInterval()
                  )

          );
          tokenBucket.tryConsume();
          Long token = tokenBucket.getAvailableToken();
        System.out.println(token);
          return  true;
    }

}
