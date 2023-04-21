package com.yellowsunn.ratelimiterboard.utils

import com.yellowsunn.ratelimiterboard.const.IP_PATTERN
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class RegexTest {
    @Test
    fun X_FORWARDED_FOR_첫번째_IP_가져오기() {
        val xForwardedForHeader: String = "111.111.111.111, 222.22.222.22, 127.0.0.1"
        val regex = Regex(IP_PATTERN)

        val matchResult = regex.find(xForwardedForHeader)

        val ip: String? = matchResult?.groups?.firstOrNull()?.value

        ip shouldBe "111.111.111.111"
    }
}
