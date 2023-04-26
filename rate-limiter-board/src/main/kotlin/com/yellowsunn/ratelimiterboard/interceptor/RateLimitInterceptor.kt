package com.yellowsunn.ratelimiterboard.interceptor

import com.yellowsunn.ratelimiterboard.const.IP_PATTERN
import com.yellowsunn.ratelimiterboard.const.X_FORWARDED_FOR
import com.yellowsunn.ratelimiterboard.utils.RateLimit
import com.yellowsunn.ratelimiterboard.utils.RateLimiterType
import com.yellowsunn.ratelimiterboard.utils.RateLimiterType.ALL
import com.yellowsunn.ratelimiterboard.utils.RateLimiterType.IP
import com.yellowsunn.ratelimiterboard.utils.RateLimits
import com.yellowsunn.ratelimits.RateLimitRule
import com.yellowsunn.ratelimits.RateLimiter
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import java.time.Duration
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor

@Component
class RateLimitInterceptor(
    private val rateLimiter: RateLimiter,
) : HandlerInterceptor {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val rateLimits: List<RateLimit> = findRateLimitAnnotations(handler)

        for (rateLimit in rateLimits) {
            val key = getRequestKey(request, rateLimit.type)
            val isAcquired = acquireRequest(key, rateLimit)
            if (isAcquired.not()) {
                log.warn("요청 획득 실패. key={}", key)
                response.status = HttpStatus.TOO_MANY_REQUESTS.value()
                return false
            }
        }
        return true
    }

    private fun findRateLimitAnnotations(handler: Any): List<RateLimit> {
        return if (handler is HandlerMethod) {
            val rateLimits: RateLimits? = handler.getMethodAnnotation(RateLimits::class.java)
            val rateLimitList: MutableList<RateLimit> = rateLimits?.value?.toMutableList() ?: mutableListOf()

            val rateLimit: RateLimit? = handler.getMethodAnnotation(RateLimit::class.java)
            if (rateLimit != null) {
                rateLimitList += rateLimit
            }
            rateLimitList
        } else {
            emptyList()
        }
    }

    private fun acquireRequest(key: String, rateLimit: RateLimit): Boolean {
        val rateLimitRule =
            RateLimitRule(rateLimit.capacity, Duration.of(rateLimit.duration, rateLimit.timeUnit.toChronoUnit()))

        return rateLimiter.acquire(key, rateLimitRule)
    }

    private fun getRequestKey(request: HttpServletRequest, type: RateLimiterType): String {
        return when (type) {
            ALL -> {
                "uri:${request.requestURI}"
            }

            IP -> {
                val clientIp: String = request.getHeader(X_FORWARDED_FOR)
                    ?.trim()
                    ?.let { getFirstIpAddress(it) }
                    ?: request.remoteAddr

                "uri:${request.requestURI}_ip:$clientIp"
            }
        }
    }

    private fun getFirstIpAddress(xForwardedForHeader: String): String? {
        val regex = Regex(IP_PATTERN)
        return regex.find(xForwardedForHeader)?.groups?.firstOrNull()?.value
    }
}
