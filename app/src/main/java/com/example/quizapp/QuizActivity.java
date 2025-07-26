package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizapp.models.Question;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    TextView tvQuestion;
    RadioGroup rgOptions;
    RadioButton option1, option2, option3, option4;
    Button btnNext;

    List<Question> questionList;
    int currentQuestionIndex = 0;
    int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        tvQuestion = findViewById(R.id.tvQuestion);
        rgOptions = findViewById(R.id.rgOptions);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        btnNext = findViewById(R.id.btnNext);

        // Load questions - replace this with your DB or API fetch
        questionList = getSampleQuestions();

        loadQuestion(currentQuestionIndex);

        btnNext.setOnClickListener(v -> {
            int selectedOptionId = rgOptions.getCheckedRadioButtonId();
            if (selectedOptionId == -1) {
                Toast.makeText(QuizActivity.this, "Please select an option", Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton selectedRadioButton = findViewById(selectedOptionId);
            int selectedIndex = -1;

            if (selectedRadioButton == option1) selectedIndex = 1;
            else if (selectedRadioButton == option2) selectedIndex = 2;
            else if (selectedRadioButton == option3) selectedIndex = 3;
            else if (selectedRadioButton == option4) selectedIndex = 4;

            // Check answer correctness
            if (selectedIndex == questionList.get(currentQuestionIndex).getCorrectAnswerIndex()) {
                score++;
            }

            currentQuestionIndex++;

            if (currentQuestionIndex < questionList.size()) {
                loadQuestion(currentQuestionIndex);
            } else {
                // Quiz finished, go to result activity
                Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                intent.putExtra("correctAnswers", score);
                intent.putExtra("totalQuestions", questionList.size());
                startActivity(intent);
                finish();
            }
        });
    }

    private void loadQuestion(int index) {
        rgOptions.clearCheck();
        Question q = questionList.get(index);

        tvQuestion.setText(q.getQuestionText());
        option1.setText(q.getOption1());
        option2.setText(q.getOption2());
        option3.setText(q.getOption3());
        option4.setText(q.getOption4());

        if (index == questionList.size() - 1) {
            btnNext.setText("Submit");
        } else {
            btnNext.setText("Next");
        }
    }

    // Sample questions - replace with your real data source
    private List<Question> getSampleQuestions() {
        List<Question> list = new ArrayList<>();
        list.add(new Question("What is 2+2?", "3", "4", "5", "6", 2));
        list.add(new Question("Capital of France?", "London", "Berlin", "Paris", "Madrid", 3));
        list.add(new Question("Which planet is closest to the Sun?", "Venus", "Earth", "Mercury", "Mars", 3));
        // Add more questions as needed
        return list;
    }
}
