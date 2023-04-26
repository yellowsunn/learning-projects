package com.yellowsunn.ratelimiterboard.controller.advice

import com.yellowsunn.ratelimiterboard.exception.RateLimitException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GeneralControllerAdvice {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(RateLimitException::class)
    protected fun handleIllegalArgumentException(e: RateLimitException): ResponseEntity<String> {
        log.warn("message={}", e.message)

        val headers = HttpHeaders().apply {
            set("X-RateLimit-Burst-Capacity", e.burstCapacity.toString())
        }
        return ResponseEntity
            .status(HttpStatus.TOO_MANY_REQUESTS.value())
            .headers(headers)
            .build()
    }
}
