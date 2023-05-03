package com.yellowsunn.ratelimiter.config

import com.yellowsunn.ratelimits.RateLimiter
import com.yellowsunn.ratelimits.RateLimiterFactory
import com.yellowsunn.ratelimits.RedisRateLimiterFactory
import io.lettuce.core.RedisClient
import org.redisson.Redisson
import org.redisson.config.Config
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RateLimiterConfiguration(
    @Value("\${spring.data.redis.host:localhost}") private val redisHost: String,
    @Value("\${spring.data.redis.port:6379}") private val redisPort: Int,
) {
    @Bean
    fun getRedisRateLimiterFactory(): RateLimiterFactory {
        val redisUri = "redis://$redisHost:$redisPort"
        val redissonConfig = Config().apply {
            useSingleServer().address = redisUri
        }

        return RedisRateLimiterFactory(RedisClient.create(redisUri), Redisson.create(redissonConfig))
    }

    @Bean
    fun getRedisRateLimiter(): RateLimiter {
        return getRedisRateLimiterFactory().instance
    }
}
