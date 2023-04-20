package com.yellowsunn.ratelimiterboard.repository

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class RedisRateLimitRepository(
    private val redisTemplate: RedisTemplate<String, Long>,
) {
    fun findToken(key: String): Long? {
        return redisTemplate.opsForValue().get(key)
    }

    fun saveTokenIfAbsent(key: String, burstCapacity: Long, duration: Duration): Long {
        redisTemplate.opsForValue().setIfAbsent(key, burstCapacity, duration)
        return burstCapacity
    }

    fun decreaseToken(key: String): Long? {
        return redisTemplate.opsForValue().decrement(key)
    }
}
