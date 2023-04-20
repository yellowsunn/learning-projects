package com.yellowsunn.stockconcurrency.repository

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class RedisLockRepository(
    private val redisTemplate: RedisTemplate<String, String>,
) {
    fun lock(key: Long): Boolean {
        val isSuccess: Boolean? = redisTemplate.opsForValue()
            .setIfAbsent(key.toString(), "lock", Duration.ofSeconds(3L))

        return isSuccess ?: false
    }

    fun unlock(key: Long): Boolean {
        return redisTemplate.delete(key.toString())
    }
}
