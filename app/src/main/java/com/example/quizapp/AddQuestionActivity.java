package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizapp.database.QuestionDAO;
import com.example.quizapp.models.Question;

public class AddQuestionActivity extends AppCompatActivity {

    EditText etQuestion, etOption1, etOption2, etOption3, etOption4, etCorrectAnswer;
    Button btnSubmitQuestion;

    QuestionDAO questionDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        // Bind views
        etQuestion = findViewById(R.id.etQuestion);
        etOption1 = findViewById(R.id.etOption1);
        etOption2 = findViewById(R.id.etOption2);
        etOption3 = findViewById(R.id.etOption3);
        etOption4 = findViewById(R.id.etOption4);
        etCorrectAnswer = findViewById(R.id.etCorrectAnswer);
        btnSubmitQuestion = findViewById(R.id.btnSubmitQuestion);

        // Init DAO
        questionDAO = new QuestionDAO(this);

        // Submit button logic
        btnSubmitQuestion.setOnClickListener(v -> {
            String questionText = etQuestion.getText().toString().trim();
            String option1 = etOption1.getText().toString().trim();
            String option2 = etOption2.getText().toString().trim();
            String option3 = etOption3.getText().toString().trim();
            String option4 = etOption4.getText().toString().trim();
            String correctAnswerStr = etCorrectAnswer.getText().toString().trim();

            // Check for empty fields
            if (TextUtils.isEmpty(questionText) || TextUtils.isEmpty(option1) || TextUtils.isEmpty(option2)
                    || TextUtils.isEmpty(option3) || TextUtils.isEmpty(option4) || TextUtils.isEmpty(correctAnswerStr)) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Parse correct answer
            int correctAnswer;
            try {
                correctAnswer = Integer.parseInt(correctAnswerStr);
                if (correctAnswer < 1 || correctAnswer > 4) {
                    Toast.makeText(this, "Correct answer must be between 1 and 4", Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Correct answer must be a number", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create question object
            Question question = new Question(questionText, option1, option2, option3, option4, correctAnswer);

            // Insert into DB
            boolean inserted = questionDAO.insertQuestion(question);
            if (inserted) {
                Toast.makeText(this, "Question added successfully", Toast.LENGTH_SHORT).show();

                // Return to AdminDashboardActivity and refresh list
                setResult(RESULT_OK);  // Let AdminDashboardActivity know something was added
                finish();              // Finish current activity and go back
            } else {
                Toast.makeText(this, "Failed to add question", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
