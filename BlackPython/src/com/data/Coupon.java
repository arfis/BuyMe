package com.data;

import android.widget.ImageButton;

public class Coupon {
	private int id;
	private String title;
	private int used;
	private int Permission;
	private String about;
	private String price;
	private ImageButton ib;
	
	public Coupon(String title,String about, int used, int Permission,String price)
	{
		this.setTitle(title);
		this.setPermission(Permission);
		this.setUsed(used);
		this.setAbout(about);
		this.price = price;
	}
	
	public Coupon()
	{
		
	}
	public void setPrice(String price)
	{
		this.price = price;
	}
	public String getPrice()
	{
		return price;
	}
	public String getAbout()
	{
		return about;
	}
	public void setAbout(String aboutt)
	{
		about = aboutt;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int isUsed() {
		return used;
	}
	public void setUsed(int used) {
		this.used = used;
	}
	public int isPermission() {
		return Permission;
	}
	public void setPermission(int permission) {
		this.Permission = permission;
	}
}
