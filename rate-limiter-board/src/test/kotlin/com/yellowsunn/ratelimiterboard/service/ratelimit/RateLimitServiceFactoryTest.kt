package com.yellowsunn.ratelimiterboard.service.ratelimit

import com.yellowsunn.ratelimiterboard.IntegrationTest
import com.yellowsunn.ratelimiterboard.service.ratelimit.RateLimitAllService
import com.yellowsunn.ratelimiterboard.service.ratelimit.RateLimitIpService
import com.yellowsunn.ratelimiterboard.service.ratelimit.RateLimitService
import com.yellowsunn.ratelimiterboard.service.ratelimit.RateLimitServiceFactory
import com.yellowsunn.ratelimiterboard.utils.RateLimiterType
import io.kotest.matchers.types.shouldBeInstanceOf
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class RateLimitServiceFactoryTest : IntegrationTest() {
    @Autowired
    private lateinit var rateLimitServiceFactory: RateLimitServiceFactory

    @Test
    fun get_RateLimitALLService_instance() {
        // given
        // when
        val rateLimitService: RateLimitService = rateLimitServiceFactory.get(RateLimiterType.ALL)

        // then
        rateLimitService.shouldBeInstanceOf<RateLimitAllService>()
    }

    @Test
    fun get_RateLimitIpService_instance() {
        // given
        // when
        val rateLimitService: RateLimitService = rateLimitServiceFactory.get(RateLimiterType.IP)

        // then
        rateLimitService.shouldBeInstanceOf<RateLimitIpService>()
    }
}
