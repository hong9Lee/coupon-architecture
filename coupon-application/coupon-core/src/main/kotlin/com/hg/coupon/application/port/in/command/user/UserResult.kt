package com.hg.coupon.application.port.`in`.command.user

import com.hg.coupon.support.EntityId

data class UserResult(
  val userId: EntityId,
  val channel: String,
  val channelUserId: String,
) {

}
