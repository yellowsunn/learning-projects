package com.yellowsunn.ratelimiterboard.service.ratelimit

import com.yellowsunn.ratelimiterboard.const.IP_PATTERN
import com.yellowsunn.ratelimiterboard.const.X_FORWARDED_FOR
import com.yellowsunn.ratelimiterboard.repository.RedisRateLimitRepository
import com.yellowsunn.ratelimiterboard.utils.DistributionLock
import com.yellowsunn.ratelimiterboard.utils.RateLimiterType
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Service
import java.time.Duration
import java.util.concurrent.TimeUnit

@Service
class RateLimitIpService(
    redisRateLimitRepository: RedisRateLimitRepository,
) : RateLimitService(redisRateLimitRepository) {
    override fun getType(): RateLimiterType {
        return RateLimiterType.IP
    }

    override fun getRequestKey(): String {
        val request: HttpServletRequest = getHttpRequest()

        val clientIp: String = request.getHeader(X_FORWARDED_FOR)
            ?.trim()
            ?.let { getFirstIpAddress(it) }
            ?: request.remoteAddr

        return "${request.requestURI}:$clientIp"
    }

    @DistributionLock(waitTime = 5L, leaseTime = 1L, timeUnit = TimeUnit.SECONDS)
    override fun decreaseToken(key: String, burstCapacity: Long, duration: Duration): Boolean {
        return super.decreaseToken(key, burstCapacity, duration)
    }

    private fun getFirstIpAddress(xForwardedForHeader: String): String? {
        val regex = Regex(IP_PATTERN)
        return regex.find(xForwardedForHeader)?.groups?.firstOrNull()?.value
    }
}
