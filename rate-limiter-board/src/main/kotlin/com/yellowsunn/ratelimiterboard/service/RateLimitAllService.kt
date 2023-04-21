package com.yellowsunn.ratelimiterboard.service

import com.yellowsunn.ratelimiterboard.repository.RedisRateLimitRepository
import com.yellowsunn.ratelimiterboard.utils.RateLimiterType
import org.springframework.stereotype.Service

@Service
class RateLimitAllService(
    redisRateLimitRepository: RedisRateLimitRepository,
) : RateLimitService(redisRateLimitRepository) {
    override fun getType(): RateLimiterType {
        return RateLimiterType.ALL
    }

    override fun getKey(): String {
        return getHttpRequest().requestURI
    }
}
