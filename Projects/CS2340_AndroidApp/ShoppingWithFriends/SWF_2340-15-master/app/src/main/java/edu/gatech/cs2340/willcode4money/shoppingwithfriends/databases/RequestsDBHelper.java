package edu.gatech.cs2340.willcode4money.shoppingwithfriends.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.gatech.cs2340.willcode4money.shoppingwithfriends.SaleRequest;
import edu.gatech.cs2340.willcode4money.shoppingwithfriends.User;

import static edu.gatech.cs2340.willcode4money.shoppingwithfriends.databases.DatabaseStrings.RequestStrings.COLUMN_NAME_ITEM;
import static edu.gatech.cs2340.willcode4money.shoppingwithfriends.databases.DatabaseStrings.RequestStrings.COLUMN_NAME_PRICE;
import static edu.gatech.cs2340.willcode4money.shoppingwithfriends.databases.DatabaseStrings.RequestStrings.COLUMN_NAME_USER;
import static edu.gatech.cs2340.willcode4money.shoppingwithfriends.databases.DatabaseStrings.RequestStrings.CREATE_TABLE;
import static edu.gatech.cs2340.willcode4money.shoppingwithfriends.databases.DatabaseStrings.RequestStrings.DATABASE_NAME;
import static edu.gatech.cs2340.willcode4money.shoppingwithfriends.databases.DatabaseStrings.RequestStrings.DATABASE_VERSION;
import static edu.gatech.cs2340.willcode4money.shoppingwithfriends.databases.DatabaseStrings.RequestStrings.DELETE_ALL;
import static edu.gatech.cs2340.willcode4money.shoppingwithfriends.databases.DatabaseStrings.RequestStrings.DROP_TABLE;
import static edu.gatech.cs2340.willcode4money.shoppingwithfriends.databases.DatabaseStrings.RequestStrings.TABLE_NAME;

/**
 * An SQLite database helper that allows the application to save and retrieve item request information.
 */

class RequestsDBHelper extends SQLiteOpenHelper implements BaseColumns {


    public RequestsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Initializes the table in the database
     * @param db - the database to initialize
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    /**
     * Updates the database version
     * @param db - the database to update
     * @param oldVersion - the version of the old database
     * @param newVersion - the version of the new database
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        this.onCreate(db);
    }

    /**
     * Reads all users' sale requests from the disk and saves them to each user.
     * @param users a map of users who have sale requests on the disk
     */
    public void readAllRequests(Map<String, User> users) {
        SQLiteDatabase db = this.getWritableDatabase();
        for (User user : users.values()) {
            this.readRequests(db, user);
        }
        db.close();
    }

    //Loads the sale requests from the disk for a certain user
    private void readRequests(SQLiteDatabase db, User user) {
        String username = user.getUsername();
        String[] projection = {
                COLUMN_NAME_ITEM,
                COLUMN_NAME_PRICE};
        String selection = COLUMN_NAME_USER + "='" + username + "'";
        String sortOrder = COLUMN_NAME_ITEM + " DESC";
        Cursor c = db.query(TABLE_NAME,
                projection,
                selection,
                null,
                null,
                null,
                sortOrder);
        List<SaleRequest> req = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                req.add(new SaleRequest(username, c.getString(0), Double.parseDouble(c.getString(1))));
                Log.d("[Req read]", username+","+c.getString(0)+","+c.getString(1));
            } while (c.moveToNext());
        }
        c.close();
        user.setRequests(req);
    }

    /**
     * Saves the sale requests for all registered users in the map onto the disk
     * @param users a map containing the registered users in the application
     */
    public void saveAllRequests(Map<String, User> users) {
        SQLiteDatabase db = this.getWritableDatabase();
        //Problems with duplicates
        db.execSQL(DELETE_ALL);
        for (User user : users.values()) {
            this.saveRequests(db, user);
        }
        db.close();
    }

    //Saves the sale requests from a certain user to disk
    private void saveRequests(SQLiteDatabase db, User user) {
        List<SaleRequest> requests = user.getRequests();
        for (SaleRequest request : requests) {
            String owner = request.getOwner();
            String item = request.getItem();
            String price = (Double.valueOf(request.getMaxPrice())).toString();
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME_USER, owner);
            values.put(COLUMN_NAME_ITEM, item);
            values.put(COLUMN_NAME_PRICE, price);
            Log.d("[req. save]", "Saving " + request.toString());
            db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        }
    }
}
