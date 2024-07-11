rootProject.name = "coupon"

include("coupon-adapter:coupon-api")
include("coupon-adapter:coupon-consumer")

include("coupon-application:coupon-core")
include("coupon-application:coupon-edge")


include("coupon-infra:coupon-persistence")
