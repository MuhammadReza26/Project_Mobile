package com.example.myapplication.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.myapplication.Model.Destination;
import com.example.myapplication.Model.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class DatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    public static final String DATABASE_NAME = "database.db";
    public static final String TABLE_NAME = "my_table";
    public static final int DATABASE_VERSION = 1;
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String TABLE_BOOKMARK = "my_bookmark";
    public static final String BOOKMARK_COLUMN_ID = "bookmark_id";
    public static final String BOOKMARK_COLUMN_USER_ID = "user_id";
    public static final String BOOKMARK_COLUMN_DESTINATION_ID = "destination_id";
    public static final String BOOKMARK_TIMESTAMP = "bookmark_timestamp";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_PASSWORD + " TEXT )";
        db.execSQL(query);

        String query2 = "CREATE TABLE " + TABLE_BOOKMARK +
                " (" + BOOKMARK_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                BOOKMARK_COLUMN_DESTINATION_ID + " INTEGER, " +
                BOOKMARK_COLUMN_USER_ID + " INTEGER, " +
                BOOKMARK_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                "FOREIGN KEY (" + BOOKMARK_COLUMN_USER_ID + ") REFERENCES " + TABLE_NAME + "(" + COLUMN_ID + ") ON DELETE CASCADE)";
        db.execSQL(query2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKMARK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public  void insertRecord(String name, String username, String password) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        db.insert(TABLE_NAME, null, values);
    }
    public void insertBookmark(int destinationId, int userId){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BOOKMARK_COLUMN_DESTINATION_ID, destinationId);
        values.put(BOOKMARK_COLUMN_USER_ID, userId);
        db.insert(TABLE_BOOKMARK, null, values);
    }
    public int getUserId (String username) {
        SQLiteDatabase db = getReadableDatabase();
        String[]projection = {COLUMN_ID};
        String selection = COLUMN_USERNAME + " = ?";
        String[]selectionArgs = {username};
        Cursor cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, null);
        int userId = -1;
        if (cursor.moveToFirst()){
            userId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
        }
        cursor.close();
        db.close();
        return userId;
    }
    public Set<Integer> getDestinationByUserIdBookmark(int userId){
        Set<Integer> destinationSet = new LinkedHashSet<>();
        SQLiteDatabase db = getReadableDatabase();
        String[]columns = {BOOKMARK_COLUMN_DESTINATION_ID};
        String selection = BOOKMARK_COLUMN_USER_ID + " = ?";
        String []selectionArgs = {String.valueOf(userId)};

        Cursor cursor = db.query(TABLE_BOOKMARK, columns, selection, selectionArgs, null, null, BOOKMARK_COLUMN_ID + " DESC");
        if (cursor.moveToFirst()) {
            do {
                int destinationId = cursor.getInt(cursor.getColumnIndexOrThrow(BOOKMARK_COLUMN_DESTINATION_ID));
                destinationSet.add(destinationId);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return destinationSet;
    }
    public boolean deleteBookmark(int destinationId, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_BOOKMARK, BOOKMARK_COLUMN_DESTINATION_ID + "=? AND " + BOOKMARK_COLUMN_USER_ID + "=?",
                new String[]{String.valueOf(destinationId), String.valueOf(userId)}) > 0;
    }

    public boolean isUsernameExists(String username) {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {COLUMN_USERNAME};
        String selection = COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = getReadableDatabase();
        String selection = COLUMN_USERNAME + " = ? AND " + COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }
    public User getUserInfo(String username) {
        SQLiteDatabase db = getReadableDatabase();
        String selection = COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
            String Username = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME));
            cursor.close();
            return new User(name, Username);
        } else {
            if (cursor != null) {
                cursor.close();
            }
            return null;
        }
    }
    public void deleteDatabase() {
        context.deleteDatabase(DATABASE_NAME);
        Log.d("DatabaseHelper", "Database deleted");
    }
}
