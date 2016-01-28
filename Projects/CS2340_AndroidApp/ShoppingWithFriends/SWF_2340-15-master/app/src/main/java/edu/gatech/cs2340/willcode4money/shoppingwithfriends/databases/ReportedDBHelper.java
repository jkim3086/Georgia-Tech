package edu.gatech.cs2340.willcode4money.shoppingwithfriends.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.gatech.cs2340.willcode4money.shoppingwithfriends.SaleReport;

import static edu.gatech.cs2340.willcode4money.shoppingwithfriends.databases.DatabaseStrings.ReportStrings.COLUMN_NAME_ITEM;
import static edu.gatech.cs2340.willcode4money.shoppingwithfriends.databases.DatabaseStrings.ReportStrings.COLUMN_NAME_LOCATION;
import static edu.gatech.cs2340.willcode4money.shoppingwithfriends.databases.DatabaseStrings.ReportStrings.COLUMN_NAME_OWNER;
import static edu.gatech.cs2340.willcode4money.shoppingwithfriends.databases.DatabaseStrings.ReportStrings.COLUMN_NAME_PRICE;
import static edu.gatech.cs2340.willcode4money.shoppingwithfriends.databases.DatabaseStrings.ReportStrings.CREATE_TABLE;
import static edu.gatech.cs2340.willcode4money.shoppingwithfriends.databases.DatabaseStrings.ReportStrings.DATABASE_NAME;
import static edu.gatech.cs2340.willcode4money.shoppingwithfriends.databases.DatabaseStrings.ReportStrings.DATABASE_VERSION;
import static edu.gatech.cs2340.willcode4money.shoppingwithfriends.databases.DatabaseStrings.ReportStrings.DELETE_ALL;
import static edu.gatech.cs2340.willcode4money.shoppingwithfriends.databases.DatabaseStrings.ReportStrings.DROP_TABLE;
import static edu.gatech.cs2340.willcode4money.shoppingwithfriends.databases.DatabaseStrings.ReportStrings.TABLE_NAME;

/**
 * An SQLite database helper that allows the application to save and retrieve sale report information.
 */

class ReportedDBHelper extends SQLiteOpenHelper implements BaseColumns {


    public ReportedDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Initializes the table in this database
     * @param db - the database in which to create the table
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("REPORTS DB", "ON CREATE CALLED");
        db.execSQL(CREATE_TABLE);
    }

    /**
     * Updates the database when a newer version is created
     * @param db - the database to update
     * @param oldVersion - the version of the old database
     * @param newVersion - the version of the new database
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    /**
     * Reads from disk all users' sale reports
     */
    public Map<String, List<SaleReport>> readAllReports() {
        Map<String, List<SaleReport>> reports = new HashMap<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] projection = {
                COLUMN_NAME_ITEM,
                COLUMN_NAME_OWNER,
                COLUMN_NAME_PRICE,
                COLUMN_NAME_LOCATION};
        String sortOrder = COLUMN_NAME_ITEM + " DESC";
        Cursor c = db.query(TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder);
        if (c.moveToFirst()) {
            do {
                String item = c.getString(0);
                String owner = c.getString(1);
                String location = c.getString(3);
                double price = Double.parseDouble(c.getString(2));
                Log.d("Read Reports", "Read:" + item + "," + owner + "," + price + "," + location + "!");
                if (!reports.containsKey(item)) {
                    List<SaleReport> list = new ArrayList<>();
                    list.add(new SaleReport(owner, item, price, location));
                    reports.put(item, list);
                } else {
                    reports.get(item).add(new SaleReport(owner, item, price, location));
                }
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return reports;
    }

    /**
     * Saves to disk all items' sale reports
     * @param reports -- the bucket of reports to save
     */
    public void saveAllReports(Map<String, List<SaleReport>> reports) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(DELETE_ALL);
        for (List<SaleReport> list : reports.values()) {
            this.saveReports(db, list);
        }
        db.close();
    }

    //Saves the sale reports for an item to the disk
    private void saveReports(SQLiteDatabase db, List<SaleReport> list) {
        for (SaleReport report : list) {
            String owner = report.getOwner();
            String item = report.getItem();
            String price = (Double.valueOf(report.getPrice())).toString();
            String location = report.getLocation();
            Log.d("Save Reports", "Saved:" + item + "," + owner + "," + price + "," + location + "!");
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME_OWNER, owner);
            values.put(COLUMN_NAME_ITEM, item);
            values.put(COLUMN_NAME_PRICE, price);
            values.put(COLUMN_NAME_LOCATION, location);
            db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);
        }
    }
}
