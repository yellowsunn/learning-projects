package com.yellowsunn.ratelimiterboard.service.ratelimit

import com.yellowsunn.ratelimiterboard.exception.RateLimitException
import com.yellowsunn.ratelimiterboard.repository.RedisRateLimitRepository
import com.yellowsunn.ratelimiterboard.utils.RateLimiterType
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.time.Duration

abstract class RateLimitService(
    private val redisRateLimitRepository: RedisRateLimitRepository,
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    abstract fun getType(): RateLimiterType
    abstract fun getRequestKey(): String

    open fun decreaseToken(key: String, burstCapacity: Long, duration: Duration): Boolean {
        val remainToken: Long = redisRateLimitRepository.findToken(key)
            ?: redisRateLimitRepository.saveTokenIfAbsent(key, burstCapacity, duration)

        if (remainToken <= 0L) {
            throw RateLimitException(remaing = remainToken, burstCapacity = burstCapacity)
        }

        log.info("http 요청 성공. key={}", key)
        return redisRateLimitRepository.decreaseToken(key) != null
    }

    protected fun getHttpRequest(): HttpServletRequest {
        return (RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes).request
    }
}
