package utils;

import java.io.ByteArrayOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.blackpython.R;

public class PictureConverter {
	
	public static byte[] convertToByte(int i,Context context)
	{
		 byte[] bytePicture;
		 Bitmap image = getPicture(context, i);

		 ByteArrayOutputStream out = new ByteArrayOutputStream();

		 image.compress(Bitmap.CompressFormat.PNG, 100, out);
		 bytePicture = out.toByteArray();
		 
		 Log.d("CONVERTER",bytePicture.toString());
		 return bytePicture;
	}
	
	public static Bitmap getPicture(Context context, int i)
	{
		Bitmap bitmap ;
		if(i==1)
		{
			Log.d("getPicture",context.toString());
			bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.cupon_2);
		
		return bitmap;
		}
		else
			bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.cupon_1);
		
		return bitmap;
	}
}
