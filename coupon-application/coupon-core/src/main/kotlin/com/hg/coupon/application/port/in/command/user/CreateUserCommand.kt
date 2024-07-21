package com.hg.coupon.application.port.`in`.command.user

import com.hg.coupon.support.EntityId


class CreateUserCommand (
    val userId: EntityId,
    val channel: String,
    val channelUserId: String,
    val ci: String
){}
