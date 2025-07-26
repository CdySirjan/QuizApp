package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizapp.R;
import com.example.quizapp.database.QuestionDAO;
import com.example.quizapp.models.Question;

public class UpdateQuestionActivity extends AppCompatActivity {

    private EditText etQuestion, etOption1, etOption2, etOption3, etOption4;
    private Spinner spinnerCorrect;
    private Button btnUpdate;

    private int questionId;
    private QuestionDAO questionDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_question);

        // Initialize views
        etQuestion = findViewById(R.id.etQuestion);
        etOption1 = findViewById(R.id.etOption1);
        etOption2 = findViewById(R.id.etOption2);
        etOption3 = findViewById(R.id.etOption3);
        etOption4 = findViewById(R.id.etOption4);
        spinnerCorrect = findViewById(R.id.spinnerCorrect);
        btnUpdate = findViewById(R.id.btnUpdate);

        // Setup spinner with options "Option 1" to "Option 4"
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"Option 1", "Option 2", "Option 3", "Option 4"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCorrect.setAdapter(adapter);

        // Get data passed from intent
        Intent intent = getIntent();
        questionId = intent.getIntExtra("id", -1);
        etQuestion.setText(intent.getStringExtra("question"));
        etOption1.setText(intent.getStringExtra("option1"));
        etOption2.setText(intent.getStringExtra("option2"));
        etOption3.setText(intent.getStringExtra("option3"));
        etOption4.setText(intent.getStringExtra("option4"));
        // Spinner selection is zero-based; your correctAnswer is probably 1-based, so subtract 1
        spinnerCorrect.setSelection(intent.getIntExtra("correct", 1) - 1);

        questionDAO = new QuestionDAO(this);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String questionText = etQuestion.getText().toString().trim();
                String option1 = etOption1.getText().toString().trim();
                String option2 = etOption2.getText().toString().trim();
                String option3 = etOption3.getText().toString().trim();
                String option4 = etOption4.getText().toString().trim();
                int correctAnswer = spinnerCorrect.getSelectedItemPosition() + 1;

                if (questionText.isEmpty() || option1.isEmpty() || option2.isEmpty() || option3.isEmpty() || option4.isEmpty()) {
                    Toast.makeText(UpdateQuestionActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                Question updatedQuestion = new Question(
                        questionId,
                        questionText,
                        option1,
                        option2,
                        option3,
                        option4,
                        correctAnswer
                );

                boolean success = questionDAO.updateQuestion(updatedQuestion);
                if (success) {
                    Toast.makeText(UpdateQuestionActivity.this, "Question updated successfully", Toast.LENGTH_SHORT).show();
                    finish();  // Close this activity and return to admin dashboard
                } else {
                    Toast.makeText(UpdateQuestionActivity.this, "Failed to update question", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
