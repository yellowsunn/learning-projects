package com.yellowsunn.ratelimiterboard.facade.ratelimit

import com.yellowsunn.ratelimiterboard.service.ratelimit.RateLimitService
import com.yellowsunn.ratelimiterboard.service.ratelimit.RateLimitServiceFactory
import com.yellowsunn.ratelimiterboard.utils.RateLimiterType
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class RateLimitFacade(
    private val rateLimitServiceFactory: RateLimitServiceFactory,
) {
    fun decreaseToken(burstCapacity: Long, duration: Duration, type: RateLimiterType): Boolean {
        val rateLimitService: RateLimitService = rateLimitServiceFactory.get(type)

        return rateLimitService.decreaseToken(
            key = rateLimitService.getRequestKey(),
            burstCapacity = burstCapacity,
            duration = duration,
        )
    }
}
