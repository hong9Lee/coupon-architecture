package com.hg.coupon.exception

import org.springframework.http.HttpStatus

open class ApplicationException(
    val status: HttpStatus,
    val code: String,
    override val message: String,
    val property: Map<String, Any>? = null
) : RuntimeException() {

    companion object {
        fun ofBadRequest(errorCode: ErrorCode): Throwable = ofBadRequest(errorCode.code, errorCode.message)

        fun ofBadRequest(errorCode: String, errorMessage: String): Throwable =
            ApplicationException(HttpStatus.BAD_REQUEST, errorCode, errorMessage)
    }
}
