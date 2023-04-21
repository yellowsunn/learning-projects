package com.yellowsunn.ratelimiterboard.utils

import com.yellowsunn.ratelimiterboard.service.RateLimitService
import com.yellowsunn.ratelimiterboard.service.RateLimitServiceFactory
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.time.Duration

@Aspect
@Component
class RateLimiterAspect(
    private val rateLimitServiceFactory: RateLimitServiceFactory,
) {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @Around("@annotation(RateLimiter)")
    fun limitRequestRate(joinPoint: ProceedingJoinPoint): Any? {
        val rateLimiter: RateLimiter = (joinPoint.signature as MethodSignature).method
            .getAnnotation(RateLimiter::class.java)

        val replenishDuration = Duration.of(rateLimiter.replenishTime, rateLimiter.timeUnit.toChronoUnit())

        val rateLimitService: RateLimitService = rateLimitServiceFactory.get(rateLimiter.type)

        val isSuccess = rateLimitService.decreaseToken(
            burstCapacity = rateLimiter.burstCapacity,
            duration = replenishDuration,
        )

        log.info("요청 성공 여부: {}", isSuccess)
        return joinPoint.proceed()
    }
}
