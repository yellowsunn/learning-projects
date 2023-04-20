package com.yellowsunn.ratelimiterboard.exception

class RateLimitException(
    val remaing: Long,
    val burstCapacity: Long,
) : IllegalStateException("Requests are limited due to too many requests.")
