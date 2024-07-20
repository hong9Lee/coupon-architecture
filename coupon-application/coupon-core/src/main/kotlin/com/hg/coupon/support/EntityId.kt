package com.hg.coupon.support

import java.util.UUID

data class EntityId(
        val value: String = UUID.randomUUID().toString()
)
