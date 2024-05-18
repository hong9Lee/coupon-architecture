package com.hg.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@ComponentScan(basePackages = ["com.hg.core", "com.hg.api", "com.hg.persistence", "com.hg.domain"])
@EnableJpaRepositories(basePackages = ["com.hg.persistence.repository"])
@EntityScan("com.hg.persistence.entity")
class CouponApiApplication

fun main(args: Array<String>) {
    runApplication<CouponApiApplication>(*args)
}
