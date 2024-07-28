import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    val kotlinVersion = "1.9.22"
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
}

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = false

dependencies {
    implementation(project(":coupon-application:coupon-core"))
    implementation(project(":coupon-application:coupon-edge"))

    implementation(project(":coupon-infra:coupon-kafka"))
    implementation(project(":coupon-infra:coupon-persistence"))
    implementation(project(":coupon-infra:coupon-redis-cache"))
    implementation(project(":coupon-infra:coupon-orm-adapter"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("software.aws.rds:aws-mysql-jdbc:1.1.1")
    implementation("org.springframework.kafka:spring-kafka")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

}
