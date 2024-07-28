package com.hg.coupon.application.port.out.message

import com.hg.coupon.application.domain.SendDownloadAsyncCouponResult
import java.util.concurrent.CompletableFuture

interface SendDownloadAsyncCouponBrokerPort {
    fun send(request: SendDownloadAsyncCouponRequest): CompletableFuture<SendDownloadAsyncCouponResult>
}
