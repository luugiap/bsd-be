package com.example.bds.config.rateLimit;

public interface TokenBucket {

    boolean tryConsume(long token);
    long getAvailableToken();
    boolean tryConsume();
}
