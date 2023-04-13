package com.yellowsunn.kafkagracefulshutdown

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KafkaGracefulShutdownApplication

fun main(args: Array<String>) {
    runApplication<KafkaGracefulShutdownApplication>(*args)
}
