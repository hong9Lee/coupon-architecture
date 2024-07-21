package com.hg.coupon.application.port.`in`.command.user

data class FindUserCommand(
  val channel: String,
  val channelUserId: String,
  val ci: String
) {
}
