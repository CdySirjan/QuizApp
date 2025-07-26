package com.example.quizapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizapp.database.UserDAO;
import com.example.quizapp.models.User;

public class SignupActivity extends AppCompatActivity {

    EditText emailInput, passwordInput;
    Button signupBtn;

    UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        signupBtn = findViewById(R.id.signupBtn);

        userDAO = new UserDAO(this); // Initialize DAO

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    User newUser = new User(email, password);
                    boolean success = userDAO.insertUser(newUser);

                    if (success) {
                        Toast.makeText(SignupActivity.this, "Signup successful!", Toast.LENGTH_SHORT).show();
                        finish(); // Close signup and return to login
                    } else {
                        Toast.makeText(SignupActivity.this, "User already exists!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
