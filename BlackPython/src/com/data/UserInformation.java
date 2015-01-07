package com.data;

import com.facebook.widget.LoginButton;

public class UserInformation {
	static String name;
	static Object email;
	static String id;
	static int used = 0;
	static int usedCoupons;
	static boolean loggedIn;
	static LoginButton loggingButton;
	static MemoryStorage memory;
	
	public static MemoryStorage getMemory() {
		return memory;
	}

	public static void setMemory(MemoryStorage memory) {
		UserInformation.memory = memory;
	}

	public static void CouponIncrement()
	{
		used++;
	}
	
	public static LoginButton getLoggingButton() {
		return loggingButton;
	}
	public static void setLoggingButton(LoginButton loggingButton) {
		UserInformation.loggingButton = loggingButton;
	}
	public static boolean isLoggedIn() {
		return loggedIn;
	}
	public static void setLoggedIn(boolean loggedIn) {
		UserInformation.loggedIn = loggedIn;
	}
	public static String getName() {
		return name;
	}
	public static void setName(String namee) {
		name = namee;
	}
	public static Object getEmail() {
		return email;
	}
	public static void setEmail(Object object) {
		email = object;
	}
	public static String getId() {
		return id;
	}
	public static void setId(String idd) {
		id = idd;
	}
	public static int getUsedCoupons() {
		return used;
	}
	
}
