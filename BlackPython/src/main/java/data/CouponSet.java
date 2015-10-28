package data;

import java.util.ArrayList;
import java.util.List;

public class CouponSet {
	static List<DBCoupon> coupons;
	static List<DBCoupon> golden;

	public static List<DBCoupon> getCoupons() {
		return coupons;
	}
	public static void removeCoupon(int position)
	{
		coupons.remove(position);
	}
	public static void setCoupons(List<DBCoupon> couponss) {
		coupons = couponss;
	}
	public static List<DBCoupon> getGolden(){ return golden;}

}
