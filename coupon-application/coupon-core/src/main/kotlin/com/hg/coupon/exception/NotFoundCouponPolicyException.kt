package com.hg.coupon.exception

import org.springframework.http.HttpStatus

class NotFoundCouponPolicyException(
    code: String = ErrorCode.NOT_FOUND_COUPON_POLICY.code,
    message: String = ErrorCode.NOT_FOUND_COUPON_POLICY.message
) : ApplicationException(HttpStatus.BAD_REQUEST, code, message) {

    companion object {
        fun of(): NotFoundCouponPolicyException {
            return NotFoundCouponPolicyException()
        }
    }
}

