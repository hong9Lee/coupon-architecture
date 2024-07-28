package com.hg.coupon.application.port.`in`.command

interface DownloadAsyncCouponUseCase {
    fun downloadRequest(sendDownloadAsyncCouponCommand: SendDownloadAsyncCouponCommand)
}
