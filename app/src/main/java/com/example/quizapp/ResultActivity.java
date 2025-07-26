package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    TextView tvScore;
    Button btnFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tvScore = findViewById(R.id.tvScore);
        btnFinish = findViewById(R.id.btnFinish);

        // Get score from Intent extras
        Intent intent = getIntent();
        int correctAnswers = intent.getIntExtra("correctAnswers", 0);
        int totalQuestions = intent.getIntExtra("totalQuestions", 10);  // default 10

        // Display score
        tvScore.setText("Your Score: " + correctAnswers + "/" + totalQuestions);

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Option 1: Finish this activity and go back to UserDashboard or MainActivity
                finish();

                // Option 2: If you want to explicitly start UserDashboardActivity, uncomment below:
                /*
                Intent dashboardIntent = new Intent(ResultActivity.this, UserDashboardActivity.class);
                dashboardIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(dashboardIntent);
                finish();
                */
            }
        });
    }
}
