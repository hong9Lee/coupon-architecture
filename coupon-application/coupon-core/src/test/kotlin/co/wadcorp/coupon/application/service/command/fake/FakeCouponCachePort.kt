package co.wadcorp.coupon.application.service.command.fake

import com.hg.coupon.application.port.out.cache.CouponCachePort
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentHashMap.newKeySet
import java.util.concurrent.atomic.AtomicLong

class FakeCouponCachePort : CouponCachePort {

    private val storage = ConcurrentHashMap<String, String>()
    private val counters = ConcurrentHashMap<String, AtomicLong>()
    private val setStorage = ConcurrentHashMap<String, MutableSet<String>>()
    private val hashStorage = ConcurrentHashMap<String, ConcurrentHashMap<String, String>>()

    override fun getCouponStock(key: String): String? {
        return counters["coupon_stock:$key"]?.get()?.toString()
    }

    override fun setCouponStock(key: String, value: String) {
        storage["coupon_stock:$key"] = value
        counters["coupon_stock:$key"] = AtomicLong(value.toLong())
    }

    override fun addToUsersCouponSet(key: String, value: String): Long {
        val set = setStorage.computeIfAbsent("user_coupons:$key") { newKeySet() }
        if (!set.add(value)) return -1
        return set.size.toLong()
    }

    override fun removeFromUsersCouponSet(key: String, value: String): Long? {
        val set = setStorage["user_coupons:$key"]
        set?.remove(value)
        return set?.size?.toLong()
    }

    override fun getUsersCouponSet(key: String): Set<String>? {
        return setStorage["user_coupons:$key"]
    }

    override fun incrementValue(key: String): Long? {
        return counters.computeIfAbsent("coupon_stock:$key") { AtomicLong(0) }.incrementAndGet()
    }

    override fun decrementValue(key: String): Long {
        val counter = counters.computeIfAbsent("coupon_stock:$key") { AtomicLong(0) }
        val newValue = counter.decrementAndGet()
        if (newValue < 0) {
            counter.incrementAndGet() // 재고 복구
            return -1
        }
        return newValue
    }

    override fun delete(key: String) {
        storage.remove(key)
        setStorage.remove(key)
        counters.remove(key)
    }

}



