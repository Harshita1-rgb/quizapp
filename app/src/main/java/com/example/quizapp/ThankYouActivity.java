package com.example.quizapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ThankYouActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you);

        TextView finalScoreTextView = findViewById(R.id.final_score);
        Button exitButton = findViewById(R.id.exit_button);

        // Retrieve the score passed from MainActivity
        String score = getIntent().getStringExtra("SCORE");
        if (score != null) {
            finalScoreTextView.setText("Your Score: " + score); // Display the score
        }

        exitButton.setOnClickListener(v -> {
            finish(); // Exit the activity when the "Exit" button is clicked
        });
    }
}
