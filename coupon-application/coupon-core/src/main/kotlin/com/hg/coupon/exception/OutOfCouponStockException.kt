package com.hg.coupon.exception

import org.springframework.http.HttpStatus

class OutOfCouponStockException(
    code: String = ErrorCode.OUT_OF_COUPON_STOCK.code,
    message: String = ErrorCode.OUT_OF_COUPON_STOCK.message
) : ApplicationException(HttpStatus.BAD_REQUEST, code, message) {

    companion object {
        fun of(): OutOfCouponStockException {
            return OutOfCouponStockException()
        }
    }
}

