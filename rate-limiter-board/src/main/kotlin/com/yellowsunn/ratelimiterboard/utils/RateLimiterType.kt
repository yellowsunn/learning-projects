package com.yellowsunn.ratelimiterboard.utils

enum class RateLimiterType(
    // 적용된 처리율 제한자가 여러개일 경우 우선 순위가 정해짐
    val order: Long,
) {
    IP(order = 1L),
    ALL(order = Long.MAX_VALUE),
}
