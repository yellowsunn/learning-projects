package com.yellowsunn.ratelimiterboard.controller

import com.yellowsunn.ratelimiterboard.utils.RateLimiter
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.TimeUnit

@RestController
class HelloController {

    @RateLimiter(burstCapacity = 10, replenishTime = 60, timeUnit = TimeUnit.SECONDS)
    @GetMapping("/hello")
    fun hello(): String {
        return "hello"
    }
}
