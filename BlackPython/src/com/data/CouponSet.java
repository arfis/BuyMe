package com.data;

import java.util.List;

public class CouponSet {
	static List<Coupon> coupons;

	public static List<Coupon> getCoupons() {
		return coupons;
	}

	public static void setCoupons(List<Coupon> couponss) {
		coupons = couponss;
	}
	
}
