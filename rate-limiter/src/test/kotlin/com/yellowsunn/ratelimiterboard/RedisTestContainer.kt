package com.yellowsunn.ratelimiterboard

import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.Network
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

@Testcontainers
interface RedisTestContainer {
    companion object {
        private val network = Network.builder()
            .build()

        @Container
        private val REDIS_CONTAINER: GenericContainer<*> =
            GenericContainer(DockerImageName.parse("redis").withTag("6-alpine"))
                .withNetwork(network)
                .withExposedPorts(6379)
                .apply { start() }

        @JvmStatic
        @DynamicPropertySource
        fun config(registry: DynamicPropertyRegistry) {
            registry.add("spring.data.redis.host") { REDIS_CONTAINER.host }
            registry.add("spring.data.redis.port") { REDIS_CONTAINER.getMappedPort(6379).toString() }
        }
    }
}
