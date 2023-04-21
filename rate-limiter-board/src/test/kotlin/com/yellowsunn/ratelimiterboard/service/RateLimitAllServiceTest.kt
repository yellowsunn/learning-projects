package com.yellowsunn.ratelimiterboard.service

import com.yellowsunn.ratelimiterboard.IntegrationTest
import io.kotest.matchers.shouldBe
import java.time.Duration
import java.util.UUID
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

class RateLimitAllServiceTest : IntegrationTest() {
    @Autowired
    private lateinit var sut: RateLimitAllService

    @BeforeEach
    fun setUp() {
        val request = MockHttpServletRequest().apply {
            requestURI = "/test-uri-${UUID.randomUUID()}"
        }
        RequestContextHolder.setRequestAttributes(ServletRequestAttributes(request))
    }

    @Test
    fun 모든_요청_처리율_제한장치_토큰_감소() {
        // when
        val isSuccess = sut.decreaseToken(burstCapacity = 10, duration = Duration.ofSeconds(3L))

        // then
        isSuccess shouldBe true
    }
}
