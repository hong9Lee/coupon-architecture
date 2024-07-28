import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    val kotlinVersion = "1.9.22"
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion

    id("net.croz.apicurio-registry-gradle-plugin") version "1.1.0"
    id("com.github.davidmc24.gradle.plugin.avro") version "1.9.1"
    id("com.github.davidmc24.gradle.plugin.avro-base") version "1.9.1"
}

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true

dependencies {
    implementation(project(":coupon-application:coupon-core"))
    implementation(project(":coupon-application:coupon-edge"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.kafka:spring-kafka")
    implementation("org.springframework.retry:spring-retry")

    api("io.confluent:kafka-streams-avro-serde:7.6.0")
    api("io.confluent:kafka-avro-serializer:7.6.0")
    api("io.apicurio:apicurio-registry-serdes-avro-serde:2.4.14.Final")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.kafka:spring-kafka-test")
}

// Kafka & Avro
avro {
    isCreateSetters = false
    isCreateOptionalGetters = true
    fieldVisibility = "PRIVATE"
    outputCharacterEncoding = "UTF-8"
    stringType = "String"
}

tasks.test {
    useJUnitPlatform()
    dependsOn("generateAvroJava")
}
