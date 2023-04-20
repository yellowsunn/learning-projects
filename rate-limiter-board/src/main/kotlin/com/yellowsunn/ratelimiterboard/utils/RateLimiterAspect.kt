package com.yellowsunn.ratelimiterboard.utils

import jakarta.servlet.http.HttpServletRequest
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.time.Duration

@Aspect
@Component
class RateLimiterAspect {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @Around("@annotation(RateLimiter)")
    fun limitRequestRate(joinPoint: ProceedingJoinPoint) {
        val rateLimiter: RateLimiter = (joinPoint.signature as MethodSignature).method
            .getAnnotation(RateLimiter::class.java)

        val request: HttpServletRequest =
            (RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes).request

        val replenishDuration = Duration.of(rateLimiter.replenishTime, rateLimiter.timeUnit.toChronoUnit())

        joinPoint.proceed()
    }
}
