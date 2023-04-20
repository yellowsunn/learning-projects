package com.yellowsunn.ratelimiterboard

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RateLimiterBoardApplication

fun main(args: Array<String>) {
    runApplication<RateLimiterBoardApplication>(*args)
}
