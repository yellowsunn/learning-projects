package com.yellowsunn.ratelimiterboard.service.ratelimit

import com.yellowsunn.ratelimiterboard.IntegrationTest
import com.yellowsunn.ratelimiterboard.const.X_FORWARDED_FOR
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.time.Duration
import java.util.UUID

class RateLimitIpServiceTest : IntegrationTest() {
    @Autowired
    private lateinit var sut: RateLimitIpService

    @BeforeEach
    fun setUp() {
        val request = MockHttpServletRequest().apply {
            requestURI = "/test-uri-${UUID.randomUUID()}"
            addHeader(X_FORWARDED_FOR, "111.111.111.111, 222.22.222.22, 127.0.0.1")
        }
        RequestContextHolder.setRequestAttributes(ServletRequestAttributes(request))
    }

    @Test
    fun IP_처리율_제한장치_토큰_감소() {
        // when
        val isSuccess =
            sut.decreaseToken(key = sut.getRequestKey(), burstCapacity = 10, duration = Duration.ofSeconds(5L))

        // then
        isSuccess shouldBe true
    }
}
