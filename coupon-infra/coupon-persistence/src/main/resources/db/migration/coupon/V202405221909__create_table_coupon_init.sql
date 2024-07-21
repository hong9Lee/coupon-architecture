create table coupon
(
    seq                     bigint auto_increment comment '쿠폰 시퀀스' primary key,
    coupon_id               varchar(64)    not null comment '쿠폰 ID',
    coupon_policy_id        varchar(64)    not null comment '쿠폰 정책 ID',
    user_id                 varchar(64)    not null comment '유저 ID',
    coupon_name             varchar(64)    not null comment '쿠폰 이름',
    coupon_usage_status     varchar(64)    not null comment '쿠폰 사용 상태(사용 가능 상태, 사용된 상태, 만료 상태)',
    discount_value          decimal(19, 2) not null comment '할인 금액',
    coupon_start_date_time  datetime(6) not null comment '쿠폰 적용 시작 일자',
    coupon_expire_date_time datetime(6) not null comment '쿠폰 만료 일자',
    reg_date_time           datetime(6) null comment '등록일자',
    mod_date_time           datetime(6) null comment '수정일자'
) comment '쿠폰 테이블';



create table coupon_policy
(
    seq                     bigint auto_increment comment '쿠폰 정책 시퀀스' primary key,
    coupon_policy_id        varchar(64)    not null comment '쿠폰 정책 ID',
    coupon_policy_status    varchar(64)    not null comment '쿠폰 발금 상태(승인 대기-DRAFT, 승인-ARRPOVE, 반려-REJECTION, 발행-PUBLISH, 종료-TERMINATION, 만료-EXPIRED)',
    coupon_name             varchar(64)    not null comment '내부 관리용 쿠폰명',
    discount_value          decimal(19, 2) not null comment '할인 금액',
    coupon_start_date_time  datetime(6) not null comment '쿠폰 적용 시작 일자',
    coupon_expire_date_time datetime(6) not null comment '쿠폰 만료 일자',
    reg_date_time           datetime(6) null comment '등록일자',
    mod_date_time           datetime(6) null comment '수정일자'
) comment '쿠폰 정책 테이블';

create table coupon_stock
(
    seq              bigint auto_increment comment '쿠폰 재고 시퀀스' primary key,
    coupon_policy_id varchar(64) not null comment '쿠폰 ID',
    coupon_type      varchar(64) not null comment '쿠폰 타입(재고 제한-STOCKED, 재고 무제한-UNLIMITED)',
    stock            varchar(64) not null comment '초기 설정한 재고 개수',
    sell_stock       varchar(64) not null comment '판매된 개수',
    reg_date_time    datetime(6) null comment '등록일자',
    mod_date_time    datetime(6) null comment '수정일자'
) comment '쿠폰 재고 테이블';

create table coupon_user
(
    seq             bigint auto_increment comment '쿠폰 유저 시퀀스' primary key,
    user_id         varchar(64) not null comment '유저 ID',
    channel         varchar(64) not null comment '채널 이름',
    channel_user_id varchar(64) not null comment '채널 유저 ID',
    reg_date_time   datetime(6) null comment '등록일자'
)
    comment '쿠폰 유저';
