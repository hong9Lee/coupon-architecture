package com.hg.coupon.domain

import java.math.BigDecimal
import java.math.RoundingMode

data class Amount(
    val value: BigDecimal = BigDecimal.ZERO,
) {
    constructor(value: Long) : this(
        value = BigDecimal(value)
    )

    fun isRemain(): Boolean {
        return value > BigDecimal.ZERO
    }

    operator fun plus(amount: Amount): Amount {
        return Amount(this.value.plus(amount.value))
    }

    operator fun minus(amount: Amount): Amount {
        return Amount(this.value.minus(amount.value))
    }

    operator fun times(times: Int): Amount {
        return Amount(this.value.times(BigDecimal(times)))
    }

    operator fun compareTo(amount: Amount): Int {
        return this.value.compareTo(amount.value)
    }

    operator fun div(amount: Amount): Amount {
        return Amount(this.value.div(amount.value))
    }

    override fun toString(): String {
        return this.value.toString()
    }

    fun negative(): Amount {
        return Amount(this.value.negate())
    }

    companion object {
        val ZERO = Amount()
    }

    fun multiplyPercent(amount: Amount): Amount {
        /** TODO: 정책 확인 후 수정 필요 */
        val discountRate = amount.value.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)
        val discountedValue = this.value.multiply(discountRate).setScale(0, RoundingMode.DOWN)
        return Amount(discountedValue)
    }

    fun isOver(amount: Amount): Boolean {
        if (this.value < amount.value) {
            return true;
        }
        return false;
    }

    fun isUnder(amount: Amount): Boolean {
        if (this.value > amount.value) {
            return true;
        }
        return false;
    }
}
