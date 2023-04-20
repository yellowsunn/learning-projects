package com.yellowsunn.ratelimiterboard.utils

import com.yellowsunn.ratelimiterboard.service.RateLimitService
import java.time.Duration
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Aspect
@Component
class RateLimiterAspect(
    private val rateLimitService: RateLimitService,
) {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @Around("@annotation(RateLimiter)")
    fun limitRequestRate(joinPoint: ProceedingJoinPoint) {
        val rateLimiter: RateLimiter = (joinPoint.signature as MethodSignature).method
            .getAnnotation(RateLimiter::class.java)

//        val request: HttpServletRequest =
//            (RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes).request

        val replenishDuration = Duration.of(rateLimiter.replenishTime, rateLimiter.timeUnit.toChronoUnit())
        val isSuccess = rateLimitService.decreaseToken(
            key = "test",
            burstCapacity = rateLimiter.burstCapacity,
            duration = replenishDuration,
        )

        log.info("성공? {}", isSuccess)
        joinPoint.proceed()
    }
}
