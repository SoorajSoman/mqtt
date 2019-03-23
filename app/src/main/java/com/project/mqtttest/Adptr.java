package com.project.mqtttest;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;


public class Adptr {

	public static final String PUBLIC_URL = "";

    private static final String DATABASE_NAME = "Robot";

    private static final int DATABASE_VERSION = 1;
    private static final String SCRIPT_CREATE_ITEM = "create table item_tbl (id integer primary key autoincrement,item_category VARCHAR not null,item_id VARCHAR not null,item_name VARCHAR not null,item_description VARCHAR not null, item_image integer not null, expiry_date  DATETIME DEFAULT CURRENT_TIMESTAMP , item_price DEC(65,2) not null,total_qty integer not null , rfid VARCHAR not null );";
    private static final String SCRIPT_CREATE_CART_TABLE = "create table cart_tbl (item_id VARCHAR not null ,item_name VARCHAR not null,item_image integer not null,quantity integer not null,rfid VARCHAR not null,price DEC(65,2) not null,total_price DEC(65,2) not null);";
    private static final String SCRIPT_CREATE_BILL = "create table bill_tbl (item_id VARCHAR not null ,item_name VARCHAR not null,quantity VARCHAR not null,price DEC(65,2) not null,total_price DEC(65,2) not null);";


    Context cd;
	private Dbhelper ck;
	private SQLiteDatabase dk;

	public Adptr(Context paramContext) {
		this.cd = paramContext;
	}

	public void close() {		this.ck.close();}

    public Adptr read() {
        this.ck = new Dbhelper(this.cd, DATABASE_NAME, null, 1);
        this.dk = this.ck.getReadableDatabase();
        return this;
    }

    public Adptr write() {
        this.ck = new Dbhelper(this.cd,DATABASE_NAME, null, 1);
        this.dk = this.ck.getWritableDatabase();
        return this;
    }
                                        /**INSERTS***/



    public void insrt_items(String v0,String v1,String v2,String v3,double v4,int v5,int v6,String v7) {

        ContentValues localContentValues = new ContentValues();

        localContentValues.put("item_category",v0);
        localContentValues.put("item_id",v1);
        localContentValues.put("item_name",v2);
        localContentValues.put("item_description", v3);
        localContentValues.put("item_price", v4);
        localContentValues.put("item_image", v5);
        localContentValues.put("total_qty", v6);
        localContentValues.put("rfid",v7);
        dk.insert("item_tbl", null, localContentValues);

    }

    public long insrt_cart( String paramString2,
                           String paramString3, int paramString4, float paramString5,String rfid,int img) {

        float tt=paramString4*paramString5;
        ContentValues localContentValues = new ContentValues();

        localContentValues.put("item_id", paramString2);
        localContentValues.put("item_name", paramString3);
        localContentValues.put("quantity", paramString4);
        localContentValues.put("price", paramString5);
        localContentValues.put("rfid", rfid);
        localContentValues.put("item_image",img);
        localContentValues.put("total_price",tt );
        return   this.dk.insert("cart_tbl", null, localContentValues);
    }


    public Cursor getallitems(String desc) {
        //  String query ="select sum(bank_bal) as total,account_no,bank_id, max(bal_date) as date from bank_tbl group by account_no";
        String query ="SELECT * from item_tbl where item_category like '"+desc+"'" ;

        return this.dk.rawQuery(query,null);
    }
    public Cursor getallcartitems(String id) {
        //  String query ="select sum(bank_bal) as total,account_no,bank_id, max(bal_date) as date from bank_tbl group by account_no";
        String query ="SELECT * from cart_tbl where item_id= '"+id+"'" ;

        return this.dk.rawQuery(query,null);
    }

    public Cursor getcartitems() {
        //  String query ="select sum(bank_bal) as total,account_no,bank_id, max(bal_date) as date from bank_tbl group by account_no";
        String query ="SELECT * from cart_tbl " ;

        return this.dk.rawQuery(query,null);
    }


    public class Dbhelper extends SQLiteOpenHelper {

        private Dbhelper sinstance=null;

        public Dbhelper getSinstance (Context context)
        {
            if (sinstance == null) {
                sinstance = new Dbhelper(context.getApplicationContext(),null,null,0);
            }
            return sinstance;
        }


        private Dbhelper(Context context, String name, CursorFactory factory,
                         int version)
        {

            super(context,name,factory, version);
        }


        @Override
        public void onCreate(SQLiteDatabase db){
            db.execSQL(SCRIPT_CREATE_ITEM);
            db.execSQL(SCRIPT_CREATE_BILL);
            db.execSQL(SCRIPT_CREATE_CART_TABLE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db,int oldversion,int newversion){
            db.execSQL("Drop table"+SCRIPT_CREATE_ITEM);
            db.execSQL("Drop table"+SCRIPT_CREATE_CART_TABLE);
            db.execSQL("Drop table"+SCRIPT_CREATE_BILL);

        }


    }

}