package com.yellowsunn.ratelimiterboard.exception

class RateLimitException(
    key: String = "",
    val burstCapacity: Long,
) : IllegalStateException("Requests are limited due to too many requests. key=$key")
