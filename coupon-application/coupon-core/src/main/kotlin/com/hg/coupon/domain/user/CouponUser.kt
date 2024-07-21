package com.hg.coupon.domain.user

import com.hg.coupon.exception.ApplicationException
import com.hg.coupon.exception.ErrorCode
import com.hg.coupon.support.EntityId

/**
 * 쿠폰 유저 도메인
 */
class CouponUser(
    val userId: EntityId, // shortUUID
    val channel: String, // ex CATCHTABLE
    val channelUserId: String, // useSeq
    val newUser: Boolean,
) {
    fun isNewUser() = newUser
    fun isExistUser() {
        if (isNewUser()) {
            throw ApplicationException.ofBadRequest(ErrorCode.NOT_FOUND_USER)
        }
    }
}
