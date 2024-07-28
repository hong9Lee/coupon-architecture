import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    val kotlinVersion = "1.9.22"
    kotlin("plugin.spring") version "1.9.22"
    kotlin("plugin.jpa") version kotlinVersion
}

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true

dependencies {
    // domain & application
    implementation(project(":coupon-application:coupon-core"))
    implementation(project(":coupon-application:coupon-edge"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-cache")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
