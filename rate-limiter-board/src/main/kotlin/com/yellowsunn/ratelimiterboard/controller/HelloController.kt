package com.yellowsunn.ratelimiterboard.controller

import com.yellowsunn.ratelimiterboard.utils.RateLimiter
import com.yellowsunn.ratelimiterboard.utils.RateLimiterType.ALL
import com.yellowsunn.ratelimiterboard.utils.RateLimiterType.IP
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.TimeUnit

@RestController
class HelloController {
    @RateLimiter(type = ALL, burstCapacity = 30, replenishTime = 10, timeUnit = TimeUnit.SECONDS)
    @RateLimiter(type = IP, burstCapacity = 10, replenishTime = 1, timeUnit = TimeUnit.MINUTES)
    @GetMapping("/hello")
    fun hello(): String {
        return "hello"
    }

    @RateLimiter(type = ALL, burstCapacity = 100, replenishTime = 1, timeUnit = TimeUnit.SECONDS)
    @GetMapping("/hello2")
    fun hello2(): String {
        return "hello2"
    }
}
