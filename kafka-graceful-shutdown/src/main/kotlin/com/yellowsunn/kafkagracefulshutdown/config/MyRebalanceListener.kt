package com.yellowsunn.kafkagracefulshutdown.config

import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.common.TopicPartition
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.listener.ConsumerAwareRebalanceListener
import org.springframework.stereotype.Component

@Component
class MyRebalanceListener : ConsumerAwareRebalanceListener {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    override fun onPartitionsAssigned(
        consumer: Consumer<*, *>,
        partitions: Collection<TopicPartition>,
    ) {
        log.info("Partitions assigned.")
        super.onPartitionsAssigned(consumer, partitions)
    }

    override fun onPartitionsRevokedBeforeCommit(
        consumer: Consumer<*, *>,
        partitions: MutableCollection<TopicPartition>,
    ) {
        log.info("Partitions revoked.")
        super.onPartitionsRevokedBeforeCommit(consumer, partitions)
    }
}
