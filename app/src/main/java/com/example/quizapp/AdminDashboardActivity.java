package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp.adapter.QuestionAdapter;
import com.example.quizapp.database.QuestionDAO;
import com.example.quizapp.models.Question;

import java.util.List;

public class AdminDashboardActivity extends AppCompatActivity implements QuestionAdapter.OnQuestionActionListener {

    private static final int REQUEST_CODE_ADD_QUESTION = 1;

    private Button btnAddQuestion;
    private RecyclerView rvQuestions;

    private QuestionDAO questionDAO;
    private QuestionAdapter questionAdapter;
    private List<Question> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        btnAddQuestion = findViewById(R.id.btnAddQuestion);
        rvQuestions = findViewById(R.id.rvQuestions);

        questionDAO = new QuestionDAO(this);

        btnAddQuestion.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddQuestionActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD_QUESTION);
        });

        rvQuestions.setLayoutManager(new LinearLayoutManager(this));

        loadQuestions();
    }

    private void loadQuestions() {
        questionList = questionDAO.getAllQuestions();
        if (questionList.isEmpty()) {
            Toast.makeText(this, "No questions available", Toast.LENGTH_SHORT).show();
        }
        questionAdapter = new QuestionAdapter(this, questionList, this); // 'this' as listener
        rvQuestions.setAdapter(questionAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_QUESTION && resultCode == RESULT_OK) {
            // Refresh questions list after adding new question
            loadQuestions();
        }
    }

    @Override
    public void onUpdate(Question question) {
        Intent intent = new Intent(this, UpdateQuestionActivity.class);
        intent.putExtra("id", question.getId());
        intent.putExtra("question", question.getQuestionText());
        intent.putExtra("option1", question.getOption1());
        intent.putExtra("option2", question.getOption2());
        intent.putExtra("option3", question.getOption3());
        intent.putExtra("option4", question.getOption4());
        intent.putExtra("correct", question.getCorrectAnswer());
        startActivity(intent);
    }

    @Override
    public void onDelete(Question question) {
        boolean deleted = questionDAO.deleteQuestion(question.getId());
        if (deleted) {
            Toast.makeText(this, "Question deleted", Toast.LENGTH_SHORT).show();
            loadQuestions(); // Refresh list after delete
        } else {
            Toast.makeText(this, "Failed to delete", Toast.LENGTH_SHORT).show();
        }
    }
}
