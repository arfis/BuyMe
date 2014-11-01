package com.data;

import android.widget.ImageButton;

public class Coupon {
	private int id;
	private String title;
	private int used;
	private int Permission;
	private ImageButton ib;
	
	public Coupon(String title, int used, int Permission)
	{
		this.setTitle(title);
		this.setPermission(Permission);
		this.setUsed(used);
		
	}
	
	public Coupon()
	{
		
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
