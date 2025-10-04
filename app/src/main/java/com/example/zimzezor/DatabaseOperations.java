package com.example.zimzezor;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;
public class DatabaseOperations {
    private final DatabaseHelper dbHelper;

    public DatabaseOperations(Context context) {
        this.dbHelper = new DatabaseHelper(context);
    }

    // Kayıt var mı kontrol et
    public boolean isRecordExists(String name) {
        if (name == null) return false;

        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = dbHelper.getReadableDatabase();
            cursor = db.query(DatabaseHelper.TABLE_NAME,
                    new String[]{DatabaseHelper.COLUMN_NAME},
                    DatabaseHelper.COLUMN_NAME + "=?",
                    new String[]{name},
                    null, null, null);
            return cursor != null && cursor.getCount() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (cursor != null) cursor.close();
            if (db != null) db.close();
        }
    }
    public boolean addUrlToDatabase(String url, String name) {
        if (url == null || name == null) return false;
//vt kayıt
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_URL, url);
            values.put(DatabaseHelper.COLUMN_NAME, name);
            return db.insert(DatabaseHelper.TABLE_NAME, null, values) != -1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (db != null) db.close();
        }
    }
    public List<String[]> getAllUrls() {
        List<String[]> urlList = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;
//video
        try {
            db = dbHelper.getReadableDatabase();
            cursor = db.query(DatabaseHelper.TABLE_NAME,
                    null, null, null,
                    null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
                    @SuppressLint("Range") String url = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_URL));
                    @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME));
                    urlList.add(new String[]{id, url, name});
                } while (cursor.moveToNext());
            }
            return urlList;
        } catch (Exception e) {
            e.printStackTrace();
            return urlList;
        } finally {
            if (cursor != null) cursor.close();
            if (db != null) db.close();
        }
    }
    public String getUrlByName(String name) {
        if (name == null) return "";

        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = dbHelper.getReadableDatabase();
            cursor = db.query(DatabaseHelper.TABLE_NAME,
                    new String[]{DatabaseHelper.COLUMN_URL},
                    DatabaseHelper.COLUMN_NAME + "=?",
                    new String[]{name},
                    null, null, null);

            return (cursor != null && cursor.moveToFirst())
                    ? cursor.getString(0)
                    : "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {
            if (cursor != null) cursor.close();
            if (db != null) db.close();
        }
    }
}