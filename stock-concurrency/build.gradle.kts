plugins {
    kotlin("plugin.jpa") version "1.7.22"
}

group = "com.yellowsunn"
version = "0.0.1-SNAPSHOT"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
//    runtimeOnly("com.h2database:h2")
    runtimeOnly("com.mysql:mysql-connector-j")
}
