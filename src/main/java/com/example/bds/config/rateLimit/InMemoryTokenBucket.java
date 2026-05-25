package com.example.bds.config.rateLimit;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Data
public class InMemoryTokenBucket implements TokenBucket {
    private long capacity;
    private long refillBucketToken;
    private long reFillIntervalMillis;
    private long availableTokens;
    private long lastTimeRefill;

    public InMemoryTokenBucket(long capacity, long refillBucket, long reFillIntervalMillis) {
        this.capacity = capacity;
        this.refillBucketToken = refillBucket;
        this.reFillIntervalMillis = reFillIntervalMillis;
        this.availableTokens = capacity;
        this.lastTimeRefill = System.currentTimeMillis();
    }
    @Override
    public synchronized boolean tryConsume(long token) {
        refill();
        if(availableTokens >= token) {
            availableTokens -= token;
            return true;
        }
        return false;
    }



    @Override
    public synchronized long getAvailableToken() {
        refill();
        return availableTokens;


    }

    @Override
    public boolean tryConsume() {
        return tryConsume(3);
    }

    public synchronized void refill(){

        long now = System.currentTimeMillis();
        long elapsed = now - lastTimeRefill;
        long intervals = elapsed / reFillIntervalMillis;

        long tokensToAdd = intervals * refillBucketToken;
        if(tokensToAdd > 0){
            availableTokens = Math.min(capacity, availableTokens + tokensToAdd);
            lastTimeRefill += intervals * reFillIntervalMillis;
        }

    }



}



