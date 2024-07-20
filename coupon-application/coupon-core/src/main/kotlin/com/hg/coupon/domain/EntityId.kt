package com.hg.coupon.domain

import java.util.UUID

data class EntityId(
        val value: String = UUID.randomUUID().toString()
)
