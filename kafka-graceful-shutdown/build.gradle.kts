plugins {
    kotlin("plugin.jpa") version "1.7.22"
}

group = "com.yellowsunn"
version = "0.0.1-SNAPSHOT"

dependencies {
    implementation("org.springframework.kafka:spring-kafka")
    implementation("com.fasterxml.jackson.core:jackson-databind")
    runtimeOnly("com.h2database:h2")
    testImplementation("org.springframework.kafka:spring-kafka-test")
}
