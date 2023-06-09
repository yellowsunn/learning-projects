package com.yellowsunn.ratelimiter.controller

import com.yellowsunn.ratelimiter.utils.RateLimit
import com.yellowsunn.ratelimiter.utils.RateLimiterType.ALL
import com.yellowsunn.ratelimiter.utils.RateLimiterType.IP
import java.util.concurrent.TimeUnit
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {
    // 10초 동안 최대 20개의 요청만 수용
    @RateLimit(type = ALL, capacity = 20, duration = 10, timeUnit = TimeUnit.SECONDS)
    @GetMapping("/hello")
    fun hello(): String {
        return "hello"
    }

    // IP 별로 1초에 2번의 요청, 분당 최대 100개의 요청만 수용
    @RateLimit(type = IP, capacity = 2, duration = 1, timeUnit = TimeUnit.SECONDS)
    @RateLimit(type = ALL, capacity = 100, duration = 1, timeUnit = TimeUnit.MINUTES)
    @GetMapping("/hello2")
    fun hello2(): String {
        return "hello2"
    }
}
