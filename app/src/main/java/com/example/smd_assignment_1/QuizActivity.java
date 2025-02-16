package com.example.smd_assignment_1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class QuizActivity extends AppCompatActivity {

    private TextView questionText;
    private RadioGroup optionsGroup;
    private Button prevButton;
    private Button nextButton;
    private TextView userNameDisplay;

    private String userName;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private boolean[] answered;

    // Quiz questions and answers
    private final String[] questions = {
            "What is Android?",
            "Which programming language is primarily used for Android development?",
            "What is an Activity in Android?",
            "What is the purpose of AndroidManifest.xml?",
            "What is an Intent in Android?"
    };

    private final String[][] options = {
            {"An operating system", "A web browser", "A programming language", "A database"},
            {"Python", "Java", "C++", "Ruby"},
            {"A single screen in an app", "A database", "A widget", "A service"},
            {"Configuration file", "Source code file", "Resource file", "Layout file"},
            {"Message passing system", "Database query", "UI component", "Network protocol"}
    };

    private final int[] correctAnswers = {0, 1, 0, 0, 0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Initialize views
        initializeViews();

        // Get user name from intent
        userName = getIntent().getStringExtra("name");
        userNameDisplay.setText("Welcome, " + userName);

        // Initialize answered array
        answered = new boolean[questions.length];

        // Set click listeners
        setButtonListeners();

        // Load first question
        loadQuestion(0);
    }

    private void initializeViews() {
        questionText = findViewById(R.id.questionText);
        optionsGroup = findViewById(R.id.optionsGroup);
        prevButton = findViewById(R.id.prevButton);
        nextButton = findViewById(R.id.nextButton);
        userNameDisplay = findViewById(R.id.userNameDisplay);
    }

    private void setButtonListeners() {
        prevButton.setOnClickListener(v -> navigateQuestion(-1));
        nextButton.setOnClickListener(v -> navigateQuestion(1));
    }

    private void loadQuestion(int index) {
        questionText.setText(questions[index]);
        optionsGroup.clearCheck();

        // Load options
        for (int i = 0; i < optionsGroup.getChildCount(); i++) {
            ((RadioButton) optionsGroup.getChildAt(i)).setText(options[index][i]);
        }

        // Update button states
        prevButton.setEnabled(index > 0);
        nextButton.setText(index == questions.length - 1 ? "Finish" : "Next");
    }

    private void navigateQuestion(int direction) {
        // Save answer if selected
        if (optionsGroup.getCheckedRadioButtonId() != -1) {
            answered[currentQuestionIndex] = true;
            int selectedAnswer = optionsGroup.indexOfChild(findViewById(optionsGroup.getCheckedRadioButtonId()));
            if (selectedAnswer == correctAnswers[currentQuestionIndex]) {
                score++;
            }
        }

        currentQuestionIndex += direction;

        if (currentQuestionIndex == questions.length) {
            finishQuiz();
        } else {
            loadQuestion(currentQuestionIndex);
        }
    }

    private void finishQuiz() {
        Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
        intent.putExtra("SCORE", score);
        intent.putExtra("TOTAL_QUESTIONS", questions.length);
        intent.putExtra("USER_NAME", userName);
        startActivity(intent);
        finish();
    }
}