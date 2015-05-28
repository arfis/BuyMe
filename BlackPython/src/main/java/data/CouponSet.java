package data;

import java.util.ArrayList;

public class CouponSet {
	static ArrayList<Coupon> coupons;
	static ArrayList<Coupon> golden;

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
	public static ArrayList<Coupon> getGolden(){ return golden;}
	public static void addGolden(Coupon coupon){
		golden.add(coupon);
	}
	public static void removeGolden(){
		golden.clear();
	}
}
