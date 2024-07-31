package com.hg.coupon.service.validator

import com.hg.coupon.application.port.out.CouponPolicyPort
import com.hg.coupon.application.port.out.CouponPort
import com.hg.coupon.application.port.out.CouponStockPort
import com.hg.coupon.domain.coupon.CouponPolicy
import com.hg.coupon.domain.coupon.CouponStock
import com.hg.coupon.domain.coupon.enums.CouponPolicyStatus
import com.hg.coupon.domain.coupon.enums.CouponUsageStatus
import com.hg.coupon.exception.CouponAlreadyExistException
import com.hg.coupon.exception.InvalidCouponPolicyExpireDateTimeException
import com.hg.coupon.exception.InvalidCouponPolicyStatusException
import com.hg.coupon.exception.OutOfCouponStockException
import com.hg.coupon.support.EntityId
import com.hg.coupon.validator.CouponValidator
import org.springframework.stereotype.Component
import java.time.ZonedDateTime

@Component
class GeneralCouponValidator(
    private val couponPolicyPort: CouponPolicyPort,
    private val couponPort: CouponPort,
) : CouponValidator {

    /** 쿠폰 정책 validation */
    override fun validateCouponPolicy(couponPolicyId: EntityId, now: ZonedDateTime): CouponPolicy {
        val couponPolicy = couponPolicyPort.findCouponPolicyById(couponPolicyId)

        if (CouponPolicyStatus.PUBLISH != couponPolicy.couponPolicyStatus) {
            throw InvalidCouponPolicyStatusException.of()
        }

        if (now.isAfter(couponPolicy.couponExpireDateTime)
        ) {
            throw InvalidCouponPolicyExpireDateTimeException.of()
        }

        return couponPolicy
    }

    /** 발급 받은 쿠폰이 있는가? */
    override fun validateAlreadyDownloaded(couponPolicyId: EntityId, couponUserId: EntityId) {
        couponPort.findByCouponPolicyIdAndUserIdAndCouponStatus(
            couponPolicyId, couponUserId, CouponUsageStatus.USABLE
        )?.let {
            throw CouponAlreadyExistException.of()
        }
    }
}
