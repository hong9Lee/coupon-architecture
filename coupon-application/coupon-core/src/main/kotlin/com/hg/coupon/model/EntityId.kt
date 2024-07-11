package com.hg.coupon.model

import java.util.UUID

data class EntityId(
        val value: String = UUID.randomUUID().toString()
)
