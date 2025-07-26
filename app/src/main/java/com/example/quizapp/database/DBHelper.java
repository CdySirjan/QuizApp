package com.example.quizapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "QuizApp.db";
    public static final int DATABASE_VERSION = 2; // Increased version to trigger onUpgrade

    // ========================== USERS TABLE ==========================
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USER_EMAIL = "email";
    public static final String COLUMN_USER_PASSWORD = "password";

    private static final String CREATE_USERS_TABLE =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    COLUMN_USER_EMAIL + " TEXT PRIMARY KEY, " +
                    COLUMN_USER_PASSWORD + " TEXT);";

    // ========================== ADMIN TABLE ==========================
    public static final String TABLE_ADMIN = "admin";
    public static final String COLUMN_ADMIN_EMAIL = "email";
    public static final String COLUMN_ADMIN_PASSWORD = "password";

    private static final String CREATE_ADMIN_TABLE =
            "CREATE TABLE " + TABLE_ADMIN + " (" +
                    COLUMN_ADMIN_EMAIL + " TEXT PRIMARY KEY, " +
                    COLUMN_ADMIN_PASSWORD + " TEXT);";

    // ========================== QUESTIONS TABLE ==========================
    public static final String TABLE_QUESTIONS = "questions";
    public static final String COLUMN_QUESTION_ID = "id";  // Added this constant
    public static final String COLUMN_QUESTION_TEXT = "question_text";
    public static final String COLUMN_OPTION1 = "option1";
    public static final String COLUMN_OPTION2 = "option2";
    public static final String COLUMN_OPTION3 = "option3";
    public static final String COLUMN_OPTION4 = "option4";
    public static final String COLUMN_CORRECT_ANSWER = "correct_answer";

    private static final String CREATE_QUESTIONS_TABLE =
            "CREATE TABLE " + TABLE_QUESTIONS + " (" +
                    COLUMN_QUESTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_QUESTION_TEXT + " TEXT, " +
                    COLUMN_OPTION1 + " TEXT, " +
                    COLUMN_OPTION2 + " TEXT, " +
                    COLUMN_OPTION3 + " TEXT, " +
                    COLUMN_OPTION4 + " TEXT, " +
                    COLUMN_CORRECT_ANSWER + " INTEGER);";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_ADMIN_TABLE);
        db.execSQL(CREATE_QUESTIONS_TABLE);

        // Insert default admin credentials
        ContentValues adminValues = new ContentValues();
        adminValues.put(COLUMN_ADMIN_EMAIL, "sirjan@gmail.com");
        adminValues.put(COLUMN_ADMIN_PASSWORD, "1234");
        db.insert(TABLE_ADMIN, null, adminValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADMIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);
        onCreate(db);
    }
}
