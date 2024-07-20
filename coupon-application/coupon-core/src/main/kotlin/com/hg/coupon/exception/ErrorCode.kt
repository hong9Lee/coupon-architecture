package com.hg.coupon.exception

enum class ErrorCode(
    val code: String,
    val message: String,
) {
    INVALID_COUPON_POLICY_STATUS("INVALID_COUPON_POLICY_STATUS", "발급 가능한 쿠폰 상태가 아닙니다."),
    INVALID_COUPON_POLICY_EXPIRE_DATE_TIME(
        "INVALID_COUPON_POLICY_EXPIRE_DATE_TIME",
        "쿠폰이 만료되어 다운로드 받을 수 없습니다."
    ),
    INVALID_COUPON_EXPIRE_DATE_TIME(
        "INVALID_COUPON_EXPIRE_DATE_TIME",
        "만료된 쿠폰입니다."
    ),
    INVALID_COUPON_USAGE("INVALID_COUPON_USAGE", "유효한 쿠폰 발급 내역을 찾을 수 없습니다."),
    INVALID_COUPON_USABLE_DATE_TIME("INVALID_COUPON_USABLE_DATE_TIME", "사용 가능 기간이 아닌 쿠폰입니다."),

    ALREADY_ISSUED_COUPON("ALREADY_ISSUED_COUPON", "이미 발급받은 쿠폰입니다."),
    OUT_OF_COUPON_STOCK("OUT_OF_COUPON_STOCK", "쿠폰 재고가 없습니다."),
    NOT_FOUND_USER("NOT_FOUND_USER", "유저 정보를 찾지 못했습니다."),
    NOT_FOUND_COUPON("NOT_FOUND_COUPON", "발급 받은 쿠폰 정보를 찾지 못했습니다."),
    NOT_FOUND_COUPON_STOCK("NOT_FOUND_COUPON_STOCK", "쿠폰 재고 정보를 찾지 못했습니다."),
    NOT_FOUND_COUPON_POLICY("NOT_FOUND_COUPON_POLICY", "쿠폰 정책 정보를 찾지 못했습니다."),

    NOT_FOUND_COUPON_EXECUTION_COST("NOT_FOUND_COUPON_EXECUTION_COST", "쿠폰 집행 금액 정보를 찾지 못했습니다."),
    EXCEED_EXECUTION_COST("EXCEED_EXECUTION_COST", "집행 금액이 초과하여 쿠폰을 다운로드 받을 수 없습니다."),

    INVALID_AMOUNT("INVALID_AMOUNT", "유효하지 않은 금액입니다."),
    EXCEED_MAX_DISCOUNT_PRICE("EXCEED_MAX_DISCOUNT_PRICE", "최대 사용 금액을 초과했습니다."),
    BELOW_MIN_PAY_PRICE("BELOW_MIN_PAY_PRICE", "최소 사용 금액 이하입니다."),
    DOWNLOAD_COUPON_ERROR("DOWNLOAD_COUPON_ERROR", "쿠폰 발급에 실패했습니다. 다시 시도해주세요.")
}
