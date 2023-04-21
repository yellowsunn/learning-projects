package com.yellowsunn.ratelimiterboard.service.ratelimit

import com.yellowsunn.ratelimiterboard.utils.RateLimiterType
import org.springframework.stereotype.Component

@Component
class RateLimitServiceFactory(rateLimitServices: List<RateLimitService>) {
    private val rateLimitServiceMap: Map<RateLimiterType, RateLimitService> =
        rateLimitServices.associateBy { it.getType() }

    fun get(type: RateLimiterType): RateLimitService {
        return rateLimitServiceMap[type]
            ?: throw IllegalArgumentException("해당 타입의 RateLimitService 구현체를 찾을 수 없습니다. type=$type")
    }
}
