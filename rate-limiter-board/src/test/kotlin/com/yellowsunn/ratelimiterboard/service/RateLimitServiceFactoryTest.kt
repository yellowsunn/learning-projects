package com.yellowsunn.ratelimiterboard.service

import com.yellowsunn.ratelimiterboard.IntegrationTest
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
