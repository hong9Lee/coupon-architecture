import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    val kotlinVersion = "1.9.22"
    kotlin("plugin.spring") version "1.9.22"
    kotlin("plugin.jpa") version kotlinVersion
    kotlin("kapt")
}

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true

val flywayVersion by extra { "10.4.1" }
val queryDslVersion by extra { "5.1.0" }

dependencies {
    // domain & application
    implementation(project(":coupon-application:coupon-core"))
    implementation(project(":coupon-application:coupon-edge"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("software.aws.rds:aws-mysql-jdbc:1.1.1")
    implementation("com.h2database:h2")

    // QueryDSL & JPA
    val queryDslVersion = dependencyManagement.importedProperties["querydsl.version"]
    implementation("com.querydsl:querydsl-jpa:${queryDslVersion}:jakarta")
    // querydsl JPAAnnotationProcessor 사용 지정
    kapt("com.querydsl:querydsl-apt:${queryDslVersion}:jakarta")
    annotationProcessor("jakarta.persistence:jakarta.persistence-api")
    annotationProcessor("jakarta.annotation:jakarta.annotation-api")

    // flyway
    implementation("org.flywaydb:flyway-core:$flywayVersion")
    // FlywayException: Unsupported Database: MySQL 8.0 에 대한 대응
    implementation("org.flywaydb:flyway-mysql:$flywayVersion")
    implementation("io.micrometer:micrometer-core")
    implementation("io.micrometer:micrometer-registry-prometheus")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("com.h2database:h2")
}

val queryDslSrcDir = layout.buildDirectory.dir("generated/querydsl").get().asFile

sourceSets {
    main {
        kotlin {
            srcDir(queryDslSrcDir)
        }
    }
}

tasks.withType<JavaCompile> {
    options.generatedSourceOutputDirectory.set(file(queryDslSrcDir))
}

tasks.named("clean") {
    doLast {
        file(queryDslSrcDir).deleteRecursively()
    }
}
