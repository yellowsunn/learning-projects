package com.yellowsunn.kafkagracefulshutdown.config

import org.apache.kafka.clients.admin.AdminClient
import org.apache.kafka.clients.producer.ProducerConfig
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class KafkaConfig {
    @Bean
    fun adminClient(
        @Value("\${spring.kafka.producer.bootstrap-servers}") servers: List<String>,
    ): AdminClient = AdminClient.create(
        mapOf(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to servers,
        ),
    )
}
