package com.example.quizapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizapp.AdminDashboardActivity;
import com.example.quizapp.database.DBHelper;
import com.example.quizapp.R;

public class AdminLoginActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button btnLogin;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        dbHelper = new DBHelper(this);

        editTextEmail = findViewById(R.id.editTextAdminEmail);
        editTextPassword = findViewById(R.id.editTextAdminPassword);
        btnLogin = findViewById(R.id.btnAdminLogin);

        btnLogin.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(AdminLoginActivity.this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
                return;
            }

            if (checkAdminCredentials(email, password)) {
                Intent intent = new Intent(AdminLoginActivity.this, AdminDashboardActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(AdminLoginActivity.this, "Invalid admin email or password", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean checkAdminCredentials(String email, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selection = DBHelper.COLUMN_ADMIN_EMAIL + "=? AND " + DBHelper.COLUMN_ADMIN_PASSWORD + "=?";
        String[] selectionArgs = {email, password};

        Cursor cursor = db.query(DBHelper.TABLE_ADMIN, null, selection, selectionArgs, null, null, null);
        boolean isValid = cursor.getCount() > 0;
        cursor.close();
        return isValid;
    }
}
