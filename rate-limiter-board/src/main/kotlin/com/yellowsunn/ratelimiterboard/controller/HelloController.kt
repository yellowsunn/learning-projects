package com.yellowsunn.ratelimiterboard.controller

import com.yellowsunn.ratelimiterboard.utils.RateLimit
import com.yellowsunn.ratelimiterboard.utils.RateLimiterType.ALL
import com.yellowsunn.ratelimiterboard.utils.RateLimiterType.IP
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.TimeUnit

@RestController
class HelloController {
    @RateLimit(type = ALL, capacity = 30, duration = 10, timeUnit = TimeUnit.SECONDS)
    @GetMapping("/hello")
    fun hello(@RequestParam testParam: String): String {
        return "hello"
    }

    @RateLimit(type = IP, capacity = 10, duration = 1, timeUnit = TimeUnit.MINUTES)
    @RateLimit(type = ALL, capacity = 30, duration = 1, timeUnit = TimeUnit.MINUTES)
    @GetMapping("/hello2")
    fun hello2(): String {
        return "hello2"
    }
}
