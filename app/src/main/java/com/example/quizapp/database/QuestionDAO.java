package com.example.quizapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quizapp.models.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {
    private DBHelper dbHelper;

    public QuestionDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    // Insert a new question into the database
    public boolean insertQuestion(Question question) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("question_text", question.getQuestionText());
        values.put("option1", question.getOption1());
        values.put("option2", question.getOption2());
        values.put("option3", question.getOption3());
        values.put("option4", question.getOption4());
        values.put("correct_answer", question.getCorrectAnswer());

        long id = db.insert("questions", null, values);
        db.close();
        return id != -1;
    }

    // Retrieve all questions
    public List<Question> getAllQuestions() {
        List<Question> questionList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM questions", null);

        if (cursor.moveToFirst()) {
            do {
                Question question = new Question(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("question_text")),
                        cursor.getString(cursor.getColumnIndexOrThrow("option1")),
                        cursor.getString(cursor.getColumnIndexOrThrow("option2")),
                        cursor.getString(cursor.getColumnIndexOrThrow("option3")),
                        cursor.getString(cursor.getColumnIndexOrThrow("option4")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("correct_answer"))
                );
                questionList.add(question);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return questionList;
    }

    // Delete a question by id
    public boolean deleteQuestion(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rows = db.delete("questions", "id = ?", new String[]{String.valueOf(id)});
        db.close();
        return rows > 0;
    }

    // Update an existing question
    public boolean updateQuestion(Question question) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("question_text", question.getQuestionText());
        values.put("option1", question.getOption1());
        values.put("option2", question.getOption2());
        values.put("option3", question.getOption3());
        values.put("option4", question.getOption4());
        values.put("correct_answer", question.getCorrectAnswer());

        int rows = db.update("questions", values, "id = ?", new String[]{String.valueOf(question.getId())});
        db.close();
        return rows > 0;
    }
}
