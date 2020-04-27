package com.bamtori.coupon.code;

public enum CouponStatus {

    STANDBY(0),
    PUBLISHED(1),
    USED(2),
    EXPIRED(3);

    final private int status;

    private CouponStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
