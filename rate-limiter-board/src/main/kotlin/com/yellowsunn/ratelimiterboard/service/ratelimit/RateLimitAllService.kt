package com.yellowsunn.ratelimiterboard.service.ratelimit

import com.yellowsunn.ratelimiterboard.repository.RedisRateLimitRepository
import com.yellowsunn.ratelimiterboard.utils.DistributionLock
import com.yellowsunn.ratelimiterboard.utils.RateLimiterType
import org.springframework.stereotype.Service
import java.time.Duration
import java.util.concurrent.TimeUnit

@Service
class RateLimitAllService(
    redisRateLimitRepository: RedisRateLimitRepository,
) : RateLimitService(redisRateLimitRepository) {
    override fun getType(): RateLimiterType {
        return RateLimiterType.ALL
    }

    override fun getRequestKey(): String {
        return getHttpRequest().requestURI
    }

    @DistributionLock(waitTime = 5L, leaseTime = 1L, timeUnit = TimeUnit.SECONDS)
    override fun decreaseToken(key: String, burstCapacity: Long, duration: Duration): Boolean {
        return super.decreaseToken(key, burstCapacity, duration)
    }
}
