package edu.gatech.cs2340.willcode4money.shoppingwithfriends.databases;

import android.provider.BaseColumns;

/**
 * A class to contain constant values used by the databases in this application.
 */
abstract class DatabaseStrings implements BaseColumns {
    /**
     * Contains values required by the UserDBHelper
     */
    public abstract class UserStrings {

        //Constant values
        public static final int DATABASE_VERSION = 2;
        public static final String DATABASE_NAME = "RegisteredUsers.db";
        public static final String TABLE_NAME = "users";
        public static final String COLUMN_NAME_ID = "username";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_PASSWORD = "password";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_FRIENDS = "friends";
        public static final String COLUMN_NAME_RATINGS = "ratings";
        public static final String COLUMN_NAME_AUTH = "authenticated";

        //CREATE TABLE users(_ID TEXT PRIMARY KEY, username TEXT UNIQUE, password TEXT, name TEXT, email TEXT,...
        //friends TEXT, ratings TEXT, authenticated TEXT);
        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + _ID + " TEXT PRIMARY KEY," +
                COLUMN_NAME_ID + " TEXT UNIQUE," + COLUMN_NAME_PASSWORD + " TEXT," + COLUMN_NAME_NAME + " TEXT," +
                COLUMN_NAME_EMAIL + " TEXT," + COLUMN_NAME_FRIENDS + " TEXT," + COLUMN_NAME_RATINGS + " TEXT," +
                COLUMN_NAME_AUTH + " TEXT" + ")";

        //DROP TABLE IF EXISTS users;
        public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    /**
     * Contains values required by the RequestsDBHelper
     */
    public abstract class RequestStrings {

        //Constant values
        public static final String DATABASE_NAME = "Requests.db";
        public static final int DATABASE_VERSION = 2;
        public static final String TABLE_NAME = "requests";
        public static final String COLUMN_NAME_USER = "user";
        public static final String COLUMN_NAME_ITEM = "item";
        public static final String COLUMN_NAME_PRICE = "price";

        //CREATE TABLE requests(_ID TEXT PRIMARY KEY,user TEXT,item TEXT,price TEXT)
        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + _ID +
                " TEXT PRIMARY KEY," + COLUMN_NAME_USER + " TEXT," + COLUMN_NAME_ITEM + " TEXT," +
                COLUMN_NAME_PRICE + " TEXT" + ")";

        //DROP TABLE IF EXISTS requests;
        public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

        //Delete all entries from table: DELETE FROM requests
        public static final String DELETE_ALL = "DELETE FROM " + TABLE_NAME;
    }

    /**
     * Contains values required by the ReportedDBHelper
     */
    public abstract class ReportStrings {

        //Constant values
        public static final String DATABASE_NAME = "ReportedSales.db";
        public static final int DATABASE_VERSION = 2;

        public static final String TABLE_NAME = "reports";
        public static final String COLUMN_NAME_ITEM = "item";
        public static final String COLUMN_NAME_OWNER = "owner";
        public static final String COLUMN_NAME_PRICE = "price";
        public static final String COLUMN_NAME_LOCATION = "location";

        //CREATE TABLE reports(_ID TEXT PRIMARY KEY,user TEXT
        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
                "(_ID TEXT PRIMARY KEY, " + COLUMN_NAME_ITEM + " TEXT, " +
                COLUMN_NAME_OWNER + " TEXT, " + COLUMN_NAME_PRICE + " TEXT, " +
                COLUMN_NAME_LOCATION + " TEXT)";

        //DROP TABLE IF EXISTS reports;
        public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

        //Delete all entries from table: DELETE FROM reports
        public static final String DELETE_ALL = "DELETE FROM " + TABLE_NAME;
    }
}
