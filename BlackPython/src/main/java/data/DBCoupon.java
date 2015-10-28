package data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import org.osmdroid.ResourceProxy;

/**
 * Created by Snow on 10/21/2015.
 */
/*
      "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT, "+
                "permission INTEGER," +
                "price TEXT," +
                "about TEXT, "+
                "used INTEGER, "+
                "picture BLOB, "+
                "company TEXT, "+
                "code BLOB, " +
                "opened INTEGER )"; //9
 */
public class DBCoupon extends SugarRecord<DBCoupon> {
    public String title;
    public int permission;
    public String price;
    public String about = "none";
    public String company = "none";
    public int used;
    public int opened;
    public  byte[] picture;
    public  byte[] code;
    public int previewId;

    @Ignore
    public Bitmap bpicture;
    @Ignore
    public Bitmap bcode;

    public DBCoupon(){
    }

    public DBCoupon(String title, int permission, String price, String about, int used, byte[] picture, String company, byte[] code, int opened,int localId){
        this.title = title;
        this.permission = permission;
        this.price = price;
        this.previewId = localId;
        if(about!=null) {
            this.about = about;
        }
        this.used = 0;
        this.picture = picture;

        if(company!=null) {
            this.company = company;
        }

            this.company = "none";

        this.code = code;
        this.opened = 0;
        this.bpicture = (setBitmap(picture));
        this.bcode = setBitmap(code);
    }

    public Bitmap setBitmap(byte[] blob){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferQualityOverSpeed = false;
        options.inPreferredConfig = Bitmap.Config.ARGB_4444;
        options.inScaled  = false;

        Bitmap bmp = BitmapFactory.decodeByteArray(blob, 0,blob.length,options);
        return bmp;
    }

}

