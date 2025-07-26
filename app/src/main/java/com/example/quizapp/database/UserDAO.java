package com.example.quizapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quizapp.models.User;

public class UserDAO {

    private DBHelper dbHelper;

    public UserDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    // Insert new user, returns false if user already exists
    public boolean insertUser(User user) {
        if (checkUserExists(user.getEmail())) return false;

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_USER_EMAIL, user.getEmail());
        values.put(DBHelper.COLUMN_USER_PASSWORD, user.getPassword());

        long result = db.insert(DBHelper.TABLE_USERS, null, values);
        db.close();
        return result != -1;
    }

    public boolean checkUserExists(String email) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {DBHelper.COLUMN_USER_EMAIL};
        String selection = DBHelper.COLUMN_USER_EMAIL + " = ?";
        String[] selectionArgs = {email};

        Cursor cursor = db.query(DBHelper.TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        boolean exists = cursor.moveToFirst();
        cursor.close();
        db.close();
        return exists;
    }

    public boolean validateLogin(String email, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {DBHelper.COLUMN_USER_EMAIL};
        String selection = DBHelper.COLUMN_USER_EMAIL + " = ? AND " + DBHelper.COLUMN_USER_PASSWORD + " = ?";
        String[] selectionArgs = {email, password};

        Cursor cursor = db.query(DBHelper.TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        boolean isValid = cursor.moveToFirst();
        cursor.close();
        db.close();
        return isValid;
    }

}
