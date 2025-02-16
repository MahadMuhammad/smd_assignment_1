package com.example.smd_assignment_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class ResultActivity extends AppCompatActivity {

    private TextView resultText;
    private Button shareButton;
    private String userName;
    private int score;
    private int totalQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Get data from intent
        userName = getIntent().getStringExtra("USER_NAME");
        score = getIntent().getIntExtra("SCORE", 0);
        totalQuestions = getIntent().getIntExtra("TOTAL_QUESTIONS", 5);

        // Initialize views
        resultText = findViewById(R.id.resultText);
        shareButton = findViewById(R.id.shareButton);

        score = Math.min(score, totalQuestions);
        // Display result
        String resultMessage = String.format("Congratulations %s!\nYou scored %d out of %d",
                userName, score, totalQuestions);
        resultText.setText(resultMessage);

        // Set up share button
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareResult();
            }
        });
    }

    /**
     * Creates and starts an implicit intent to share the quiz result
     */
    private void shareResult() {
        String shareMessage = String.format("Hey! I just completed the Quiz App challenge! " +
                "I scored %d out of %d questions correctly!", score, totalQuestions);

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My Quiz Result");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);

        startActivity(Intent.createChooser(shareIntent, "Share your result via"));
    }
}