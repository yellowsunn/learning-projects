package com.yellowsunn.ratelimiterboard.service

import com.yellowsunn.ratelimiterboard.exception.RateLimitException
import com.yellowsunn.ratelimiterboard.repository.RedisRateLimitRepository
import com.yellowsunn.ratelimiterboard.utils.RateLimiterType
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.time.Duration

abstract class RateLimitService(
    private val redisRateLimitRepository: RedisRateLimitRepository,
) {
    abstract fun getType(): RateLimiterType
    abstract fun getKey(): String

    fun decreaseToken(burstCapacity: Long, duration: Duration): Boolean {
        val remainToken: Long = redisRateLimitRepository.findToken(getKey())
            ?: redisRateLimitRepository.saveTokenIfAbsent(getKey(), burstCapacity, duration)

        if (remainToken <= 0L) {
            throw RateLimitException(remaing = remainToken, burstCapacity = burstCapacity)
        }

        return redisRateLimitRepository.decreaseToken(getKey()) != null
    }

    protected fun getHttpRequest(): HttpServletRequest {
        return (RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes).request
    }
}
