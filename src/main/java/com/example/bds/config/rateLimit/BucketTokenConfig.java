package com.example.bds.config.rateLimit;

import lombok.Data;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.stereotype.Component;

@Component
@Data


public class BucketTokenConfig {

   @Value("${rate.limit.capacity}")
   private long capacity;

   @Value("${rate.limit.refillToken}")
   private  long refillBucket;

   @Value("${rate.limit.refillTokenMillis}")
   private long rateFillInterval;

}
