package edu.gatech.cs2340.willcode4money.shoppingwithfriends.databases;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.gatech.cs2340.willcode4money.shoppingwithfriends.SaleReport;
import edu.gatech.cs2340.willcode4money.shoppingwithfriends.ShoppingWithFriends;
import edu.gatech.cs2340.willcode4money.shoppingwithfriends.User;

import static edu.gatech.cs2340.willcode4money.shoppingwithfriends.databases.DatabaseStrings.UserStrings.*;
/**
 * An SQLite database helper that allows the application to save and retrieve user information.
 */
public class UserDBHelper extends SQLiteOpenHelper implements BaseColumns {

    //Databases holding reported sales and item requests
    private final ReportedDBHelper reportedDBhelper;
    private final RequestsDBHelper requestsDBhelper;

    public UserDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        reportedDBhelper = new ReportedDBHelper(context);
        requestsDBhelper = new RequestsDBHelper(context);
    }

    /**
     * Initializes the tables used in this database (users table)
     * @param db - the database to initialize
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    /**
     * Updates the tables in the database if necessary
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
     * Saves the user and report information to disk for persistence
     * @param application - the Application to save
     */
    public void saveUsers(Application application) {
        Map<String, User> users = ((ShoppingWithFriends) application).getUsers();
        Map<String, List<SaleReport>> reports = ((ShoppingWithFriends) application).getReportBucket();
        SQLiteDatabase db = this.getWritableDatabase();
        for (User user : users.values()) {
            this.saveUser(db, user);
        }
        db.close();
        requestsDBhelper.saveAllRequests(users);
        reportedDBhelper.saveAllReports(reports);
    }

    /**
     * Saves the data for a specific user to the database
     * @param db - the database to save to
     * @param user - the user to save
     */
    public void saveUser(SQLiteDatabase db, User user) {
        //Save friends list as comma-separated string of usernames
        List<User> friends = user.getFriends();
        StringBuilder friendsList = new StringBuilder();
        for (User friend : friends) {
            friendsList.append(friend.getUsername());
            friendsList.append(",");
        }
        if (!friends.isEmpty()) {
            friendsList.deleteCharAt(friendsList.length() - 1);
        }
        String friendsString = friendsList.toString();
        //Save ratings as a comma-separated string of integers
        List<Integer> ratings = user.getRatingsList();
        StringBuilder rate = new StringBuilder();
        for (Integer rating : ratings) {
            rate.append(rating);
            rate.append(",");
        }
        if (!ratings.isEmpty()) {
            rate.deleteCharAt(rate.length() - 1);
        }
        String ratingString = rate.toString();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_ID, user.getUsername());
        values.put(COLUMN_NAME_PASSWORD, user.getPassword());
        values.put(COLUMN_NAME_NAME, user.getName());
        values.put(COLUMN_NAME_EMAIL, user.getEmail());
        values.put(COLUMN_NAME_FRIENDS, friendsString);
        values.put(COLUMN_NAME_RATINGS, ratingString);
        values.put(COLUMN_NAME_AUTH, (user.getAuth() ? "yes" : "no"));
        db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    /**
     * Reads user and report/request data from disk to restore on app restart.
     * @return a map containing saved users from the disk
     */
    public Map<String, User> readUsers() {
        Map<String, User> users = new HashMap<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] projection = {COLUMN_NAME_ID};
        String sortOrder = COLUMN_NAME_ID + " DESC";
        Cursor c = db.query(
                TABLE_NAME, //Name of table
                projection, //Columns to return
                null, //No selection criteria
                null, //see above
                null, //no grouping
                null, //no filters
                sortOrder);
        boolean hasUsers = c.moveToFirst(); //Make sure there are actually users to retrieve
        if (!hasUsers) {
            return users;
        }
        do {
            String username = c.getString(0);
            users.put(username, this.readUser(db, username));
        } while(c.moveToNext());
        c.close();
        //Read friends list and list of ratings
        this.readFriends(db, users);
        this.readRatings(db, users);
        db.close();
        requestsDBhelper.readAllRequests(users);
        return users;
    }

    /**
     * Reads report information from the disk
     * @return the bucket of reports
     */
    public Map<String, List<SaleReport>> readReports() {
        return reportedDBhelper.readAllReports();
    }

    //Reads the information for each user from disk.
    private User readUser(SQLiteDatabase db, String username) {
        User user;
        String password, email, name;

        String[] projection = {
                COLUMN_NAME_PASSWORD,
                COLUMN_NAME_NAME,
                COLUMN_NAME_EMAIL,
                COLUMN_NAME_AUTH};
        String selection = COLUMN_NAME_ID + "=" + "'" + username + "'";
        String sortOrder = COLUMN_NAME_NAME + " DESC";
        Cursor c = db.query(
                TABLE_NAME,
                projection,
                selection,
                null,
                null,
                null,
                sortOrder);
        c.moveToFirst();
        password = c.getString(0);
        name = c.getString(1);
        email = c.getString(2);
        user = new User(username, password, email, name);
        user.setAuth(c.getString(3).equals("yes"));
        c.close();
        return user;
    }

    //Reads the friends list of each user and reconstructs it.
    private void readFriends(SQLiteDatabase db, Map<String, User> users) {
        Set<String> usernames = users.keySet();
        String[] projection = {COLUMN_NAME_FRIENDS};
        String sortOrder = COLUMN_NAME_ID + " DESC";
        for (String username : usernames) {
            User user = users.get(username);
            String selection = COLUMN_NAME_ID + "='" + username + "'";
            Cursor c = db.query(
                    TABLE_NAME,
                    projection,
                    selection,
                    null,
                    null,
                    null,
                    sortOrder);
            c.moveToFirst();
            String[] friends = c.getString(0).split(",");
            List<User> friendsList = new ArrayList<>();
            for (String friend : friends) {
                if (friend.length() > 0) {
                    friendsList.add(users.get(friend));
                }
            }
            user.setFriends(friendsList);
            c.close();
        }
    }

    //Reads ratings information from the database and saves it to each user.
    private void readRatings(SQLiteDatabase db, Map<String, User> users) {
        Set<String> usernames = users.keySet();
        String[] projection = {COLUMN_NAME_RATINGS};
        String sortOrder = COLUMN_NAME_ID + " DESC";
        for (String username : usernames) {
            User user = users.get(username);
            String selection = COLUMN_NAME_ID + "='" + username + "'";
            Cursor c = db.query(
                    TABLE_NAME,
                    projection,
                    selection,
                    null,
                    null,
                    null,
                    sortOrder);
            c.moveToFirst();
            String[] ratings = c.getString(0).split(",");
            for (String rating : ratings) {
                if (rating.length() > 0) {
                    user.addRating(Integer.parseInt(rating));
                }
            }
            c.close();
        }
    }
}
