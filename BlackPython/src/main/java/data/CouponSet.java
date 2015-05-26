package data;

import java.util.ArrayList;

public class CouponSet {
	static ArrayList<Coupon> coupons;

	public static ArrayList<Coupon> getCoupons() {
		return coupons;
	}
	public static void removeCoupon(int position)
	{
		coupons.remove(position);
	}
	
	public static void setCoupons(ArrayList<Coupon> couponss) {
		coupons = couponss;
	}
	
}
