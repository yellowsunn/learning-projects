plugins {
    kotlin("plugin.jpa") version Versions.kotlin
    kotlin("plugin.noarg") version Versions.kotlin
}

group = "com.yellowsunn"
version = "0.0.1-SNAPSHOT"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.redisson:redisson-spring-boot-starter:${Versions.redisson}")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    runtimeOnly("com.h2database:h2")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:testcontainers:${Versions.testcontainers}")
    testImplementation("org.testcontainers:junit-jupiter:${Versions.testcontainers}")
}

noArg {
    annotation("jakarta.persistence.Entity")
}
