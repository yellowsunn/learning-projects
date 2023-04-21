package com.yellowsunn.ratelimiterboard.utils

import java.util.concurrent.TimeUnit

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class DistributionLock(
    // lockName을 지정하지 않으면 메소드명과 args의 조합으로 lockName이 정해진다.
    val lockName: String = "",
    val waitTime: Long,
    val leaseTime: Long,
    val timeUnit: TimeUnit,
)
