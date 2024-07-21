package com.hg.coupon.application.port.`in`.command.user

import com.hg.coupon.application.port.`in`.command.user.CreateUserCommand
import com.hg.coupon.application.port.`in`.command.user.UserResult

interface CreateUserUseCase {
  fun createUser(createUserCommand: CreateUserCommand): UserResult
}
