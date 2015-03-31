package fakeData;

import android.content.Context;
import android.util.Log;

import com.data.Coupon;
import com.data.MemoryStorage;
import com.data.UserInformation;
import com.utils.PictureConverter;

import java.util.ArrayList;
import java.util.List;

public class FalseCoupons {

	static List<Coupon> goldenCoupons = new ArrayList<Coupon>();
	static MemoryStorage coup;

	public static void setDatabase(Context context)
	{
		coup = UserInformation.getMemory();
		byte [] picture1;
		byte [] picture2;
		picture1 = PictureConverter.convertToByte(1,context);
		picture2 = PictureConverter.convertToByte(2,context);

		//ak je prazdna databaza tak sa vlozia nove kupony
   	 if(coup.isDatabaseEmpty() )
   	 {
        coup.addCoupon(new Coupon("Vikendovy pobyt v hoteli Kempinsky Moskva","It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point " +
        		"of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', " +
        		"making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, " +
        		"and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident," +
        		" sometimes on purpose (injected humour and the like).",0,1,"99",picture1));
        coup.addCoupon(new Coupon("20% zlava na feferony","blablabla blabla bla sladkaslksldas asldk aslk a",0,1,"23",
        		picture2));
        coup.addCoupon(new Coupon("30% Zlava na arasidy","blablabla blabla bla sladkaslksldas asldk aslk a",0,1,"53",
        		picture1));
        coup.addCoupon(new Coupon("40% zlava na feferony","blablabla blabla bla sladkaslksldas asldk aslk a",0,1,"98",picture2));
        coup.addCoupon(new Coupon("50% Zlava na arasidy","blablabla blabla bla sladkaslksldas asldk aslk a",0,1,"12",picture1));
        coup.addCoupon(new Coupon("60% zlava na feferony","blablabla blabla bla sladkaslksldas asldk aslk a",0,1,"23",picture2));
        coup.addCoupon(new Coupon("70% Zlava na arasidy","blablabla blabla bla sladkaslksldas asldk aslk a",0,1,"77",picture1));
        coup.addCoupon(new Coupon("80% zlava na feferony","blablabla blabla bla sladkaslksldas asldk aslk a",0,1,"12",picture2));
        coup.addCoupon(new Coupon("90% Zlava na arasidy","blablabla blabla bla sladkaslksldas asldk aslk a",0,1,"90",picture1));
        coup.addCoupon(new Coupon("100% zlava na feferony","blablabla blabla bla sladkaslksldas asldk aslk a",0,1,"66",picture2));
        Log.d("kurva", Integer.toString(coup.getAllCoupons().size()));
   	 }
		else{
		 Log.d("kurva","Nie je potrebne nacitavat databazu");
	 }
	}
	public static void setGolden(Context context){
				Coupon coupon = new Coupon("40% zlava na feferony","blablabla blabla bla sladkaslksldas asldk aslk a",0,1,"98",PictureConverter.convertToByte(2,context));
		coupon.setId(1);
		goldenCoupons.add(coupon);

	}
	public static List<Coupon> getGolden(){

		return goldenCoupons;
	}
}
