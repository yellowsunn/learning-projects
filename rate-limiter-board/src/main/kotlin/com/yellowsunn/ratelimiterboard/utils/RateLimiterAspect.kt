package com.yellowsunn.ratelimiterboard.utils

import com.yellowsunn.ratelimiterboard.service.RateLimitService
import com.yellowsunn.ratelimiterboard.service.RateLimitServiceFactory
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.stereotype.Component
import java.time.Duration

@Aspect
@Component
class RateLimiterAspect(
    private val rateLimitServiceFactory: RateLimitServiceFactory,
) {
    @Around("@annotation(RateLimiter)")
    fun limitRequestRate(joinPoint: ProceedingJoinPoint): Any? {
        val rateLimiter: RateLimiter = (joinPoint.signature as MethodSignature).method
            .getAnnotation(RateLimiter::class.java)

        decreaseRateLimitToken(rateLimiter)

        return joinPoint.proceed()
    }

    @Around("@annotation(RateLimiters)")
    fun limitMultipleRequestRate(joinPoint: ProceedingJoinPoint): Any? {
        val rateLimiters: Array<RateLimiter> = (joinPoint.signature as MethodSignature).method
            .getAnnotation(RateLimiters::class.java).value

        rateLimiters.forEach {
            decreaseRateLimitToken(it)
        }

        return joinPoint.proceed()
    }

    private fun decreaseRateLimitToken(rateLimiter: RateLimiter) {
        val replenishDuration = Duration.of(rateLimiter.replenishTime, rateLimiter.timeUnit.toChronoUnit())

        val rateLimitService: RateLimitService = rateLimitServiceFactory.get(rateLimiter.type)

        rateLimitService.decreaseToken(
            burstCapacity = rateLimiter.burstCapacity,
            duration = replenishDuration,
        )
    }
}
