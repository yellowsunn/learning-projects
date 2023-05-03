package com.yellowsunn.ratelimiter.interceptor

import com.yellowsunn.ratelimiter.const.X_FORWARDED_FOR
import com.yellowsunn.ratelimiter.utils.RateLimit
import com.yellowsunn.ratelimiter.utils.RateLimiterType
import com.yellowsunn.ratelimiter.utils.RateLimiterType.ALL
import com.yellowsunn.ratelimiter.utils.RateLimiterType.IP
import com.yellowsunn.ratelimiter.utils.RateLimits
import com.yellowsunn.ratelimits.RateLimitRule
import com.yellowsunn.ratelimits.RateLimiter
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import java.time.Duration

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
                    ?: request.remoteAddr

                "uri:${request.requestURI}_ip:$clientIp"
            }
        }
    }
}
