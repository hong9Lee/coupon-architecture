# Coupon-architecture
- Hexagonal 아키텍처, kotlin 기반 쿠폰 프로젝트  
쿠폰 발급 API를 통해 쿠폰 재고의 동시성, 트래픽 부하 테스트를 진행

1. 동기 방식  
쿠폰 재고 entity를 DB에서 조회할때 비관적락을 사용  
다른 세션에서 접근 타임아웃 5초, 쿠폰 발급처리 트랜잭션 타임아웃 10초로 테스트  
```
@Lock(LockModeType.PESSIMISTIC_WRITE)
@QueryHints(value = [QueryHint(name = "jakarta.persistence.lock.timeout", value = "5000")])
fun findByCouponPolicyId(couponPolicyId: EntityId): Optional<CouponStockEntity>
```


2. 비동기 방식  
redis를 사용하여 재고 관리.  
쿠폰 발급을 위한 메시지 발급은 kafka를 사용함.
순서처리가 중요하여 kafka 파티션은 1개로 테스트 진행.

테스트 시, 재고 문제  
환경: 10 Process X 100 Threads

쿠폰 재고 설정: 100,000개  
테스트 수행 결과:  
테스트 실행 횟수 (Executed Tests): 115,128 - 요청 횟수와 발행 횟수가 맞지 않음.  
요청한 유저 수: 122,302  
DB - 쿠폰 발급 수: 122,302
Redis 남은 재고: 877,698개  
Kafka 메시지 수: 122,302  
Consumer Lag: 35295 - Consumer가 메시지 처리 속도가 Producer의 메시지 발행 속도를 따라잡지 못하고 있음.
