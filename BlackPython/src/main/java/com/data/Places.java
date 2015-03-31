package com.data;

import org.osmdroid.util.GeoPoint;

import android.graphics.Bitmap;

public class Places {

	public GeoPoint point;
	public String name;
	public String about;
	public Bitmap icon;
	public int id;
	
	public GeoPoint getGeoPoint()
	{
		return point;
	}
	
	public void setGeoPoint(double latit, double longit)
	{
		point = new GeoPoint(latit,longit);
	}
}
