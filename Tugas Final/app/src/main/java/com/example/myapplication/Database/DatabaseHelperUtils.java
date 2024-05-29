package com.example.myapplication.Database;

import android.content.Context;

public class DatabaseHelperUtils {

    // Method untuk menghapus database
    public static boolean deleteDatabase(Context context) {
        return context.deleteDatabase(DatabaseHelper.DATABASE_NAME);
    }
}

