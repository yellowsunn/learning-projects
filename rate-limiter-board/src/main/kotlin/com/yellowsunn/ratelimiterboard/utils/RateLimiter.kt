package com.yellowsunn.ratelimiterboard.utils

import java.util.concurrent.TimeUnit

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class RateLimiter(
    val type: RateLimiterType,
    // 버킷 용량
    val burstCapacity: Long,
    // 다시 채우는 데 필요한 시간
    val replenishTime: Long,
    val timeUnit: TimeUnit,
)
