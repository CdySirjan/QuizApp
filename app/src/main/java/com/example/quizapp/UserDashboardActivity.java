package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UserDashboardActivity extends AppCompatActivity {

    TextView tvWelcomeUser;
    Button btnStartQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        // Initialize views
        tvWelcomeUser = findViewById(R.id.tvWelcomeUser);
        btnStartQuiz = findViewById(R.id.btnStartQuiz);

        // Show welcome message (example, you can pass actual email from login)
        String userEmail = getIntent().getStringExtra("email"); // or name if you have it
        if (userEmail != null) {
            tvWelcomeUser.setText("Welcome, " + userEmail + "!");
        }

        // Handle Start Quiz button click
        btnStartQuiz.setOnClickListener(v -> {
            Intent intent = new Intent(UserDashboardActivity.this, QuizActivity.class);
            startActivity(intent);
        });
    }
}
