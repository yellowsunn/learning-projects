package com.yellowsunn.kafkagracefulshutdown.config

import com.yellowsunn.kafkagracefulshutdown.const.TEST_TOPIC
import jakarta.annotation.PostConstruct
import java.util.UUID
import java.util.concurrent.ExecutionException
import org.apache.kafka.clients.admin.AdminClient
import org.apache.kafka.clients.admin.NewTopic
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class TestTopicInit(
    private val adminClient: AdminClient,
    private val kafkaTemplate: KafkaTemplate<String, String>,
) {
    @PostConstruct
    fun initTestTopic() = try {
        adminClient.createTopics(
            listOf(
                NewTopic(TEST_TOPIC, 3, 3),
            ),
        ).all().get()

        (1..1000).forEach {
            kafkaTemplate.send(TEST_TOPIC, UUID.randomUUID().toString(), "test-$it")
        }
    } catch (_: ExecutionException) {
        // do nothing
    }
}
