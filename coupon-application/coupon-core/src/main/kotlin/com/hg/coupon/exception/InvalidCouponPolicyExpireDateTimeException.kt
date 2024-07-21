package com.hg.coupon.exception

import org.springframework.http.HttpStatus

class InvalidCouponPolicyExpireDateTimeException(
    code: String = ErrorCode.INVALID_COUPON_POLICY_EXPIRE_DATE_TIME.code,
    message: String = ErrorCode.INVALID_COUPON_POLICY_EXPIRE_DATE_TIME.message
) : ApplicationException(HttpStatus.BAD_REQUEST, code, message) {

    companion object {
        fun of(): InvalidCouponPolicyExpireDateTimeException {
            return InvalidCouponPolicyExpireDateTimeException()
        }
    }
}

