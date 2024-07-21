package com.hg.coupon.exception

import org.springframework.http.HttpStatus

class InvalidCouponPolicyStatusException(
    code: String = ErrorCode.INVALID_COUPON_POLICY_STATUS.code,
    message: String = ErrorCode.INVALID_COUPON_POLICY_STATUS.message
) : ApplicationException(HttpStatus.BAD_REQUEST, code, message) {

    companion object {
        fun of(): InvalidCouponPolicyStatusException {
            return InvalidCouponPolicyStatusException()
        }
    }
}

