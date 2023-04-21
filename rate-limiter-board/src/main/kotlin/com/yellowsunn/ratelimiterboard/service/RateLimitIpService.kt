package com.yellowsunn.ratelimiterboard.service

import com.yellowsunn.ratelimiterboard.const.IP_PATTERN
import com.yellowsunn.ratelimiterboard.const.X_FORWARDED_FOR
import com.yellowsunn.ratelimiterboard.repository.RedisRateLimitRepository
import com.yellowsunn.ratelimiterboard.utils.RateLimiterType
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Service

@Service
class RateLimitIpService(
    redisRateLimitRepository: RedisRateLimitRepository,
) : RateLimitService(redisRateLimitRepository) {
    override fun getType(): RateLimiterType {
        return RateLimiterType.IP
    }

    override fun getKey(): String {
        val request: HttpServletRequest = getHttpRequest()

        val clientIp: String = request.getHeader(X_FORWARDED_FOR)
            ?.trim()
            ?.let { getFirstIpAddress(it) }
            ?: request.remoteAddr

        return "${request.requestURI}:$clientIp"
    }

    private fun getFirstIpAddress(xForwardedForHeader: String): String? {
        val regex = Regex(IP_PATTERN)
        return regex.find(xForwardedForHeader)?.groups?.firstOrNull()?.value
    }
}
