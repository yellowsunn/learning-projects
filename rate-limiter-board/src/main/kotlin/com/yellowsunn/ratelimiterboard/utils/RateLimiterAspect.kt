package com.yellowsunn.ratelimiterboard.utils

import com.yellowsunn.ratelimiterboard.facade.RateLimitFacade
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component
import java.time.Duration

@Aspect
@Component
class RateLimiterAspect(
    private val rateLimitFacade: RateLimitFacade,
) {
    @Around("@annotation(rateLimiter)")
    fun limitRequestRate(joinPoint: ProceedingJoinPoint, rateLimiter: RateLimiter): Any? {
        decreaseRateLimitToken(rateLimiter)
        return joinPoint.proceed()
    }

    @Around("@annotation(rateLimiters)")
    fun limitMultipleRequestRate(joinPoint: ProceedingJoinPoint, rateLimiters: RateLimiters): Any? {
        val sortedRateLimiters: List<RateLimiter> = rateLimiters.value
            .sortedBy { it.type.order }

        sortedRateLimiters.forEach {
            decreaseRateLimitToken(it)
        }
        return joinPoint.proceed()
    }

    private fun decreaseRateLimitToken(rateLimiter: RateLimiter) {
        val replenishDuration = Duration.of(rateLimiter.replenishTime, rateLimiter.timeUnit.toChronoUnit())

        rateLimitFacade.decreaseToken(
            burstCapacity = rateLimiter.burstCapacity,
            duration = replenishDuration,
            type = rateLimiter.type,
        )
    }
}
