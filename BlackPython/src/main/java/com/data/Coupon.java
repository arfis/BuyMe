package com.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageButton;

import org.osmdroid.ResourceProxy;

public class Coupon {
	private int id;
	private String title;
	private int used;
	private int Permission;
	private String about;
	private String price;
	private ImageButton ib;
	private Bitmap picture;
	private byte[] picturebyte;

	public Coupon(String title,String about, int used, int Permission,String price,byte[] picture)
	{
		this.setTitle(title);
		this.setPermission(Permission);
		this.setUsed(used);
		this.setAbout(about);
		this.price = price;
		this.picturebyte = picture;
		this.picture = setBitmap(picture);
	}
	
	public Coupon()
	{
		
	}
	public Bitmap setBitmap(byte[] blob){
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferQualityOverSpeed = false;
		options.inPreferredConfig = Bitmap.Config.ARGB_4444;
		options.inScaled  = false;
		Bitmap bmp = BitmapFactory.decodeByteArray(blob, 0,blob.length,options);
		return bmp;
	}

	public byte[] getPictureByte(){return picturebyte;	}
	public void setPicture(byte[] picture)
	{
		this.picture = setBitmap(picture);
	}
	public Bitmap getPicture()
	{
		return picture;
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
	public void setUsed(int i) {
		this.used = i;
	}
	public int isPermission() {
		return Permission;
	}
	public void setPermission(int permission) {
		this.Permission = permission;
	}
}
