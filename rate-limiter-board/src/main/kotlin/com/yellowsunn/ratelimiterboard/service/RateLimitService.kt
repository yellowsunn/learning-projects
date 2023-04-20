package com.yellowsunn.ratelimiterboard.service

import com.yellowsunn.ratelimiterboard.exception.RateLimitException
import com.yellowsunn.ratelimiterboard.repository.RedisRateLimitRepository
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class RateLimitService(
    private val redisRateLimitRepository: RedisRateLimitRepository,
) {
    fun decreaseToken(key: String, burstCapacity: Long, duration: Duration): Boolean {
        val remainToken: Long = redisRateLimitRepository.findToken(key)
            ?: redisRateLimitRepository.saveTokenIfAbsent(key, burstCapacity, duration)

        if (remainToken <= 0L) {
            throw RateLimitException(remaing = remainToken, burstCapacity = burstCapacity)
        }

        return redisRateLimitRepository.decreaseToken(key) != null
    }
}
