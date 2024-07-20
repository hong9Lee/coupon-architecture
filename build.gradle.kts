import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    val kotlinVersion = "1.9.22"
    id("org.springframework.boot") version "3.2.5"
    id("io.spring.dependency-management") version "1.1.4"
    id("org.asciidoctor.jvm.convert") version "3.3.2"
    id("org.sonarqube") version "4.0.0.2929"
    id("jacoco")

    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version "1.7.20"
}

allprojects {
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.asciidoctor.jvm.convert")
    apply(plugin = "jacoco")
    apply(plugin = "org.sonarqube")
    apply(plugin = "org.jetbrains.kotlin.plugin.serialization")
    apply(plugin = "org.jetbrains.kotlin.jvm")

    group = "co.wadcorp.coupon"
    version = "0.0.1-SNAPSHOT"
    java.sourceCompatibility = JavaVersion.VERSION_17

    val libUtilsVersion by extra { "0.0.20" }
    val loggerVersion by extra { "5.1.0" }
    val logbackVersion = "0.1.5"

    configurations {
        compileOnly {
            extendsFrom(configurations.annotationProcessor.get())
        }
    }

    repositories {
        mavenCentral()

        maven {
            url = uri("https://packages.confluent.io/maven/")
        }

//        val catchTablePackages = listOf("lib-vault", "lib-utils", "wad-libs-assembly")
//        catchTablePackages.forEach { repoName ->
//            run {
//                maven {
//                    name = "GitHubPackages_${repoName}"
//                    credentials {
//                        username = (project.findProperty("ct_username")
//                            ?: System.getenv("GITHUB_USERNAME")).toString()
//                        password = (project.findProperty("ct_token")
//                            ?: System.getenv("GITHUB_TOKEN")).toString()
//                    }
//                    url = uri("https://maven.pkg.github.com/CatchTable/${repoName}")
//                }
//            }
//        }
    }


    dependencies {

        // Libs
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlin:kotlin-reflect")

        // Logging
        implementation("io.github.oshai:kotlin-logging-jvm:5.1.0")

        // Aws
        implementation(platform("software.amazon.awssdk:bom:2.21.15"))
        implementation("software.amazon.awssdk:sts")

        // Prometheus
        implementation("io.micrometer:micrometer-core")
        implementation("io.micrometer:micrometer-registry-prometheus")

        implementation("ch.qos.logback.contrib:logback-json-classic:$logbackVersion")
        implementation("ch.qos.logback.contrib:logback-jackson:$logbackVersion")

        // logging
        implementation("io.github.oshai:kotlin-logging-jvm:$loggerVersion")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }

    tasks {
        test {
            useJUnitPlatform()
            testLogging {
                events("passed", "skipped", "failed")
            }

            finalizedBy(named("jacocoTestReport"))
        }
    }

    val jar: Jar by tasks
    val bootJar: BootJar by tasks

    bootJar.enabled = false
    jar.enabled = false
}
