package co.wadcorp.coupon.application.service.command.fake

import com.hg.coupon.application.port.out.cache.CouponCacheTransactionPort

class FakeCouponCacheTransactionPort : CouponCacheTransactionPort {
    var shouldThrowException = false

    override fun <T> executeTransaction(transactionLogic: () -> T): T {
        if (shouldThrowException) {
            throw RuntimeException("Forced exception for rollback test")
        }
        return transactionLogic()
    }
}
