package com.hg.coupon.application.port.`in`.command.user

import com.hg.coupon.domain.user.CouponUser

interface FindUserUseCase {
  fun findUser(findUserCommand: FindUserCommand): UserResult
  fun getUser(findUserCommand: FindUserCommand): CouponUser
}
