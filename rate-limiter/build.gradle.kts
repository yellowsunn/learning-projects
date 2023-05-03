group = "com.yellowsunn"
version = "0.0.1-SNAPSHOT"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")

    // redis, redisson
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.redisson:redisson-spring-boot-starter:${Versions.redisson}")

    // ratelimits library
    implementation("com.github.yellowsunn.ratelimits:ratelimits-redis:1.1.0")
    implementation("com.github.yellowsunn.ratelimits:ratelimits-core:1.1.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:testcontainers:${Versions.testcontainers}")
    testImplementation("org.testcontainers:junit-jupiter:${Versions.testcontainers}")
}
