package com.data;
import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MemoryStorage extends SQLiteOpenHelper{

	// Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "HotCouponsDB";
 
    public MemoryStorage(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);  
    }
 
    public boolean isDatabaseEmpty()
    {
    	String query = "SELECT  * FROM " + TABLE_COUPONS;
    	 
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() > 0)
    	return false;
        else return true;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create book table
        String CREATE_BOOK_TABLE = "CREATE TABLE coupons ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
                "title TEXT, "+
                "permission INTEGER," +
                "price TEXT," +
                "about TEXT, "+
                "used INTEGER )";
 
        // create books table
        db.execSQL(CREATE_BOOK_TABLE);
        Log.d("KREATE","tuto sa to vytvara");
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS coupons");
 
        // create fresh books table
        this.onCreate(db);
    }
    /**
     * CRUD operations (create "add", read "get", update, delete) book + get all books + delete all books
     */
 
    // Coupons table name
    private static final String TABLE_COUPONS = "coupons";
 
    // Coupons Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_PERMISSION = "permission";
    private static final String KEY_USED = "used";
    private static final String KEY_ABOUT = "about";
    private static final String KEY_PRICE = "price";
    //private static final String KEY_ImageButton = "imageButton";
    
    private static final String[] COLUMNS = {KEY_ID,KEY_TITLE,KEY_PERMISSION,KEY_PRICE,KEY_ABOUT,KEY_USED};
 
    public void addCoupon(Coupon coupon){
        Log.d("addCoupon", coupon.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, coupon.getTitle()); // get title 
        values.put(KEY_PERMISSION, coupon.isPermission()); // get author
        values.put(KEY_USED, coupon.isUsed());
        values.put(KEY_PRICE, coupon.getPrice());
        values.put(KEY_ABOUT, coupon.getAbout());
        // 3. insert
        db.insert(TABLE_COUPONS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values
 
        // 4. close
        db.close(); 
    }
 
    public Coupon getCoupon(int id){
 
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();
 
        // 2. build query
        Cursor cursor = 
                db.query(TABLE_COUPONS, // a. table
                COLUMNS, // b. column names
                " id = ?", // c. selections 
                new String[] { String.valueOf(id) }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
 
        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();
 
        // 4. build book object
        Coupon coupon = new Coupon();
        coupon.setId(Integer.parseInt(cursor.getString(0)));
        coupon.setTitle(cursor.getString(1));
        coupon.setPermission(Integer.parseInt(cursor.getString(2)));
        coupon.setPrice(cursor.getString(3));
        coupon.setAbout(cursor.getString(4));
        coupon.setUsed(Integer.parseInt(cursor.getString(5)));
        
        Log.d("getCoupon("+id+")", coupon.toString());
 
        // 5. return book
        return coupon;
    }
 
    // Get All Books
    public List<Coupon> getAllCoupons() {
        List<Coupon> coupons = new LinkedList<Coupon>();
 
        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_COUPONS;
 
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
 
        // 3. go over each row, build book and add it to list
        Coupon coupon = null;
        if (cursor.moveToFirst()) {
            do {
                coupon = new Coupon();
                coupon.setId(Integer.parseInt(cursor.getString(0)));
                coupon.setTitle(cursor.getString(1));
                coupon.setPermission(Integer.parseInt(cursor.getString(2)));
                coupon.setPrice(cursor.getString(3));
                coupon.setAbout(cursor.getString(4));
                coupon.setUsed(Integer.parseInt(cursor.getString(5)));
 
                // Add a coupon
                coupons.add(coupon);
            } while (cursor.moveToNext());
        }
 
        Log.d("getAllCoupons()", coupons.toString());
 
        // return books
        return coupons;
    }
 
     
    public int CouponUsed(int coupon) {
 
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("used", "1"); // get title 
 
        // 3. updating row
        int i = db.update(TABLE_COUPONS, //table
                values, // column/value
                KEY_ID+" = "+ coupon , // selections
                null); //selection args
 
        // 4. close
        db.close();
        
        List<Coupon> c = getAllCoupons();
        for(i = 0;i<c.size();i++)
        {
        	Log.d("DB vypis","Used: " + Integer.toString(c.get(i).isUsed()));
        	Log.d("DB vypis","ID: " + Integer.toString(c.get(i).getId()));
        }
        return i;
 
    }

    // Deleting single book
    public void deleteBook(Coupon coupon) {
 
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
        // 2. delete
        db.delete(TABLE_COUPONS,
                KEY_ID+" = ?",
                new String[] { String.valueOf(coupon.getId()) });
 
        // 3. close
        db.close();
 
        Log.d("deleteCoupon: ", coupon.toString());
 
    }
    public void deleteAllCoupons()
    {
    	SQLiteDatabase db = this.getWritableDatabase();
    	db.delete(TABLE_COUPONS,null,null);
    	db.close();
    }
}

