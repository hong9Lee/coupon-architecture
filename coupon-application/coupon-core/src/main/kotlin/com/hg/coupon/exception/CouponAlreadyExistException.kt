package com.hg.coupon.exception

import org.springframework.http.HttpStatus

class CouponAlreadyExistException(
    code: String = ErrorCode.ALREADY_ISSUED_COUPON.code,
    message: String = ErrorCode.ALREADY_ISSUED_COUPON.message
) : ApplicationException(HttpStatus.BAD_REQUEST, code, message) {

    companion object {
        fun of(): CouponAlreadyExistException {
            return CouponAlreadyExistException()
        }
    }
}

