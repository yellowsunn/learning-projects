package com.yellowsunn.ratelimiterboard.utils

import java.util.concurrent.TimeUnit

@JvmRepeatable(RateLimiters::class)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.TYPE)
@Retention(AnnotationRetention.RUNTIME)
annotation class RateLimiter(
    // 처리율 제한자 타입
    val type: RateLimiterType,
    // 버킷 용량
    val burstCapacity: Long,
    // 다시 채우는 데 필요한 시간
    val replenishTime: Long,
    val timeUnit: TimeUnit,
)

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class RateLimiters(
    val value: Array<RateLimiter>,
)
