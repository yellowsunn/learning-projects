package com.yellowsunn.kafkagracefulshutdown.consumer

import com.yellowsunn.kafkagracefulshutdown.const.TEST_TOPIC
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class TestTopicConsumer {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @KafkaListener(
        topics = [TEST_TOPIC],
        groupId = "test-group-id",
        concurrency = "3",
    )
    fun consumeTestTopic(@Payload message: String) {
        TimeUnit.SECONDS.sleep(5L)
        log.info("message={}", message)
    }
}
