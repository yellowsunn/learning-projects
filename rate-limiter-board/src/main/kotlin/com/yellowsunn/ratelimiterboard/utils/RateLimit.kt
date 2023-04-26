package com.yellowsunn.ratelimiterboard.utils

import java.util.concurrent.TimeUnit

@JvmRepeatable(RateLimits::class)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.TYPE)
@Retention(AnnotationRetention.RUNTIME)
annotation class RateLimit(
    // 처리율 제한자 타입
    val type: RateLimiterType,
    // 버킷 용량
    val capacity: Long,
    val duration: Long,
    val timeUnit: TimeUnit,
)

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class RateLimits(
    val value: Array<RateLimit>,
)
