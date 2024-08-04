package co.wadcorp.coupon.application.service.command

import co.wadcorp.coupon.application.service.command.fake.*
import com.hg.coupon.application.port.`in`.command.coupon.DownloadAsyncCouponCommand
import com.hg.coupon.application.port.`in`.command.coupon.DownloadCouponCommand
import com.hg.coupon.domain.coupon.CouponPolicy
import com.hg.coupon.domain.coupon.CouponStock
import com.hg.coupon.domain.coupon.enums.CouponPolicyStatus
import com.hg.coupon.domain.coupon.enums.CouponUsageStatus
import com.hg.coupon.domain.user.CouponUser
import com.hg.coupon.service.DownloadCouponService
import com.hg.coupon.support.Amount
import com.hg.coupon.support.EntityId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.ZonedDateTime
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class DownloadCouponServiceTest {

    private val couponPort = FakeCouponPort()
    private val couponPolicyPort = FakeCouponPolicyPort()
    private val couponUserPort = FakeCouponUserPort()
    private val couponStockPort = FakeCouponStockPort()
    private val couponCachePort = FakeCouponCachePort()
    private val couponCacheTransactionPort = FakeCouponCacheTransactionPort()

    private var downloadCouponService: DownloadCouponService = DownloadCouponService(
        couponPort,
        couponCachePort,
        couponCacheTransactionPort,
        couponStockPort
    )

    val couponPolicyId = EntityId("couponPolicyId")

    @BeforeEach
    fun setUp() {
        val couponStock = CouponStock(
            couponPolicyId = couponPolicyId,
            stock = 100,
            sellStock = 0
        )
        couponStockPort.addCouponStock(couponStock)

        val userId = EntityId("userId")
        val couponUser = CouponUser(
            userId = userId,
            channel = "CATCHTABLE",
            channelUserId = "123",
            newUser = true
        )
        couponUserPort.addCouponUser(couponUser)
    }

    private fun createCouponPolicy(): CouponPolicy {
        val couponPolicy = CouponPolicy(
            couponPolicyId = couponPolicyId,
            couponPolicyStatus = CouponPolicyStatus.PUBLISH,
            couponName = "testCoupon",
            discountValue = Amount(1000L),
            couponStartDateTime = ZonedDateTime.now().plusDays(1),
            couponExpireDateTime = ZonedDateTime.now().plusDays(5)
        )
        couponPolicyPort.addCouponPolicy(couponPolicy)
        return couponPolicy
    }


    @DisplayName("동기 방식 쿠폰 다운로드 시, 정확히 요청한 수 만큼 쿠폰이 발급된다.")
    @Test
    fun concurrentCouponDownloadResultsInZeroExecutionCost() {
        val now = ZonedDateTime.now()
        val couponPolicy = createCouponPolicy()

        val downloadCouponCommands = List(10) { index ->
            DownloadCouponCommand(
                couponPolicyId = couponPolicyId,
                userId = EntityId("userId$index"),
                couponId = EntityId(),
                channel = "CATCHTABLE",
                channelUserId = "user$index",
            )
        }

        val latch = CountDownLatch(1)
        val executor = Executors.newFixedThreadPool(10)

        downloadCouponCommands.forEach { command ->
            executor.submit {
                latch.await()
                downloadCouponService.downloadRequest(
                    command,
                    couponPolicy,
                    now
                )
            }
        }

        latch.countDown()
        executor.shutdown()
        executor.awaitTermination(20, TimeUnit.SECONDS)

        val remainingStock = couponStockPort.findCouponStockByCouponPolicyId(couponPolicyId)!!.sellStock
        assertEquals(10, remainingStock)

        val totalCoupons = (0 until 10).map { index ->
            couponPort.findByCouponPolicyIdAndUserIdAndCouponStatus(
                couponPolicyId,
                EntityId("userId$index"),
                CouponUsageStatus.USABLE
            )?.let { 1 } ?: 0
        }.sum()

        assertEquals(10, totalCoupons)
    }

    @DisplayName("동시에 쿠폰 재고 이상의 다운로드 요청이 들어오더라도 정확히 재고만큼만 발급이 가능하다.")
    @Test
    fun concurrent_requests_for_stock_over_coupons() {
        // given
        val initialStock = "100"
        couponCachePort.setCouponStock(couponPolicyId.value, initialStock)

        val requests = List(200) { index ->
            DownloadAsyncCouponCommand(couponPolicyId, "CATCHTABLE", "user$index")
        }

        val latch = CountDownLatch(1)
        val executor = Executors.newFixedThreadPool(200)

        requests.forEach { request ->
            executor.submit {
                latch.await()
                downloadCouponService.checkCouponDownloadable(request)
            }
        }

        // when
        latch.countDown()
        executor.shutdown()
        executor.awaitTermination(10, TimeUnit.SECONDS)

        // then
        val remainingStock = couponCachePort.getCouponStock(couponPolicyId.value)!!.toInt()
        assertEquals(0, remainingStock)

        val totalCoupons = (0 until 200).map { index ->
            couponCachePort.getUsersCouponSet("CATCHTABLE:user$index")?.size ?: 0
        }.sum()

        assertEquals(100, totalCoupons)
    }
}
