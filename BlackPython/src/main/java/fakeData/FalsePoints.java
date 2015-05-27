package fakeData;

import java.util.ArrayList;
import java.util.List;

import org.osmdroid.util.GeoPoint;

import android.graphics.Point;

import data.Places;

public class FalsePoints {

	public static List<Places> data = new ArrayList<Places>(); 
	
	public FalsePoints(){
		
		Places p;
		
			p = new Places();
			p.setGeoPoint(48.4276147, 17.6039881);
			p.about = "nejaky ten bod";
			p.name = "nieco";
			data.add(p);

			p = new Places();
			p.setGeoPoint(48.4275147, 17.8039881);
			p.about = "nejaky ten bod";
			p.name = "domov moj";
			data.add(p);

			p = new Places();
			p.setGeoPoint(48.4319478,17.792765);
			p.about = "Bernolakova";
			p.name = "Dilongova";
			data.add(p);

			p = new Places();
			p.setGeoPoint(48.4270738,17.7981562);
			p.about = "Orange";
			p.name = "Orange";
			data.add(p);

			p = new Places();
			p.setGeoPoint(48.4269191,17.7969265);
			p.about = "domov";
			p.name = "Kostol";
			data.add(p);

			p = new Places();
			p.setGeoPoint(48.4322288,17.8026746);
			p.about = "domov";
			p.name = "Kostol";
			data.add(p);


			p = new Places();
			p.setGeoPoint(48.4325235,17.7899737);
			p.about = "jedlo";
			p.name = "Tesco";
			data.add(p);

			p = new Places();
			p.setGeoPoint(48.4280088,17.8002854);
			p.about = "jedlo";
			p.name = "Jasterka";
			data.add(p);

			p = new Places();
			p.setGeoPoint(48.1789313,17.0477585);
			p.about = "jedlo";
			p.name = "Byt BA";
			data.add(p);

		p = new Places();
		p.setGeoPoint(48.1776592,17.0497179);
		p.about = "jedlo";
		p.name = "Kaufland";
		data.add(p);

		p = new Places();
		p.setGeoPoint(48.1535576,17.0712407);
		p.about = "jedlo";
		p.name = "FIIT";
		data.add(p);


	}
}
