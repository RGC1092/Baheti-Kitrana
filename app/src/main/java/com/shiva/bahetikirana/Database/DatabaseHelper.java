package com.shiva.bahetikirana.Database;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.SyncStateContract;
import android.util.Log;

import com.shiva.bahetikirana.Pojo.MyBills;
import com.shiva.bahetikirana.Pojo.ProductList;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "ProductManager";

    // Table Names
    private static final String TABLE_BILL = "bill";


    // Common column names
    private static final String ID = "id";
    private static final String BILL_NO = "bill_no";
    private static final String CREATED_AT = "created_at";
    private static final String UPDATED_AT = "updated_at";

    // Product Table - column nmaes

    private static final String TOTAL = "total";

    // BILL Table - column names
    private static final String BILL_DATE = "bill_date";
    private static final String NAME = "name";
    private static final String ADDRESS = "address";
    private static final String MOBILE = "mobile";
    private static final String PRODUCT = "product";
    private static final String QUANTITY = "quantity";
    private static final String RATE = "rate";
    private static final String SUBTOTAL = "subtotal";
    private static final String CUTTING_VALUE = "cutting_value";
    private static final String CUTTING_RATE = "cutting_rate";
    private static final String WAGES_VALUE = "wages_value";
    private static final String WAGES_RATE = "wages_rate";
    private static final String TRANSPORT_VALUE = "transport_value";
    private static final String TRANSPORT_RATE = "transport_rate";
    private static final String TOTAL_PAYABLE = "total_payable";
    private static final String PAY_BY = "pay_by";
    private static final String PAID = "paid";
    private static final String BALANCE = "balance";


    // Table Create Statements

    // Bill table create statement
    private static final String CREATE_TABLE_BILL = "CREATE TABLE " +
            TABLE_BILL + "(" +
            BILL_NO + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            BILL_DATE + " DATE," +
            NAME + " TEXT," +
            ADDRESS + " TEXT," +
            MOBILE + " TEXT," +
            PRODUCT + " TEXT," +
            QUANTITY + " REAL," +
            RATE + " REAL," +
            SUBTOTAL + " REAL," +
            CUTTING_VALUE + " REAL," +
            CUTTING_RATE + " REAL," +
            WAGES_VALUE + " REAL," +
            WAGES_RATE + " REAL," +
            TRANSPORT_VALUE + " REAL," +
            TRANSPORT_RATE + " REAL," +
            TOTAL_PAYABLE + " REAL," +
            PAY_BY + " TEXT," +
            PAID + " REAL," +
            BALANCE + " REAL," +
            CREATED_AT + " DATETIME," +
            UPDATED_AT + " DATETIME" +
            ")";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_BILL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BILL);

        // create new tables
        onCreate(db);
    }


    // ------------------------ "BILL" table methods ----------------//

    /*
     * Creating a product
     */

    public boolean insertBill(String name, String address, String mobile, String product, float quantity, float rate, float subtotal,
                              float cuttingRate, float wagesRate, float transportRate, float cuttingValue, float wagesValue, float transportValue,
                              float total_payable, String payby, float paid, float balance) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BILL_DATE, getDate());
        values.put(NAME, name);
        values.put(ADDRESS, address);
        values.put(MOBILE, mobile);
        values.put(PRODUCT, product);
        values.put(QUANTITY, quantity);
        values.put(RATE, rate);
        values.put(SUBTOTAL, subtotal);
        values.put(CUTTING_VALUE, cuttingValue);
        values.put(CUTTING_RATE, cuttingRate);
        values.put(WAGES_VALUE, wagesValue);
        values.put(WAGES_RATE, wagesRate);
        values.put(TRANSPORT_VALUE, transportValue);
        values.put(TRANSPORT_RATE, transportRate);
        values.put(TOTAL_PAYABLE, total_payable);
        values.put(PAY_BY, payby);
        values.put(PAID, paid);
        values.put(BALANCE, balance);
        values.put(CREATED_AT, getDateTime());
        values.put(UPDATED_AT, getDateTime());

        // insert row
        db.insert(TABLE_BILL, null, values);
        return true;
    }

    /**
     * getting all Bills
     */
    public List<MyBills> getAllBills() {
        List<MyBills> billLists = new ArrayList<MyBills>();
        String selectQuery = "SELECT  * FROM " + TABLE_BILL ;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                MyBills td = new MyBills();
                td.setBILL_NO(c.getInt((c.getColumnIndex(BILL_NO))));
                td.setBILL_DATE((c.getString(c.getColumnIndex(BILL_DATE))));
                td.setNAME(c.getString(c.getColumnIndex(NAME)));
                td.setADDRESS(c.getString(c.getColumnIndex(ADDRESS)));
                td.setMOBILE(c.getString(c.getColumnIndex(MOBILE)));
                td.setPRODUCT(c.getString(c.getColumnIndex(PRODUCT)));
                td.setQUANTITY(c.getFloat(c.getColumnIndex(QUANTITY)));
                td.setRATE(c.getFloat(c.getColumnIndex(RATE)));
                td.setSUBTOTAL(c.getFloat(c.getColumnIndex(SUBTOTAL)));
                td.setCUTTING_VALUE(c.getFloat(c.getColumnIndex(CUTTING_VALUE)));
                td.setCUTTING_RATE(c.getFloat(c.getColumnIndex(CUTTING_RATE)));
                td.setWAGES_VALUE(c.getFloat(c.getColumnIndex(WAGES_VALUE)));
                td.setWAGES_RATE(c.getFloat(c.getColumnIndex(WAGES_RATE)));
                td.setTRANSPORT_VALUE(c.getFloat(c.getColumnIndex(TRANSPORT_VALUE)));
                td.setTRANSPORT_RATE(c.getFloat(c.getColumnIndex(TRANSPORT_RATE)));
                td.setPAYBY(c.getString(c.getColumnIndex(PAY_BY)));
                td.setPAID(c.getFloat(c.getColumnIndex(PAID)));
                td.setBALANCE(c.getFloat(c.getColumnIndex(BALANCE)));
                td.setCREATED_AT(c.getString(c.getColumnIndex(CREATED_AT)));
                td.setUPDATED_AT(c.getString(c.getColumnIndex(UPDATED_AT)));


                // adding to todo list
                billLists.add(td);
            } while (c.moveToNext());
        }

        return billLists;
    }


    /*
     * Updating a bill
     */
    public int updateBill(String billno, String name, String address, String mobile, String product, float quantity, float rate, float subtotal,
                          float cuttingRate, float wagesRate, float transportRate, float cuttingValue, float wagesValue, float transportValue,
                          float total_payable, String payby, float paid, float balance) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BILL_NO, billno);
        values.put(NAME, name);
        values.put(ADDRESS, address);
        values.put(MOBILE, mobile);
        values.put(PRODUCT, product);
        values.put(QUANTITY, quantity);
        values.put(RATE, rate);
        values.put(SUBTOTAL, subtotal);
        values.put(CUTTING_VALUE, cuttingValue);
        values.put(CUTTING_RATE, cuttingRate);
        values.put(WAGES_VALUE, wagesValue);
        values.put(WAGES_RATE, wagesRate);
        values.put(TRANSPORT_VALUE, transportValue);
        values.put(TRANSPORT_RATE, transportRate);
        values.put(TOTAL_PAYABLE, total_payable);
        values.put(PAY_BY, payby);
        values.put(PAID, paid);
        values.put(BALANCE, balance);
        values.put(UPDATED_AT, getDateTime());

        // updating row
        return db.update(TABLE_BILL, values, BILL_NO + " = ?",
                new String[]{String.valueOf(billno)});
    }

   // SELECT bill_no FROM bill WHERE bill_no = (SELECT MAX(bill_no)  FROM bill)

    public int getBillNo(){
        int id = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM " + TABLE_BILL, null);

        if(cursor.moveToLast()){
            //name = cursor.getString(column_index);//to get other values
            id = cursor.getInt(0);//to get id, 0 is the column index
        }

        return id;
    }

    public Cursor getExportBill() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_BILL + " ",null);
        return res;
    }


    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    /**
     * get datetime
     */
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    /**
     * get date
     */
    private String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
