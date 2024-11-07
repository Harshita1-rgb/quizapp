package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button falseButton;
    private Button trueButton;
    private ImageButton nextButton;
    private ImageButton prevButton;
    private ImageView image;
    private TextView questionTextView;
    private TextView firstLastQuestionTextView;
    private int correct = 0;
    private int currentQuestionIndex = 0;

    // Full Image URLs
    String[] imageUrls = {
            "https://img.freepik.com/free-photo/planets-solar-system_23-2150042476.jpg",
            "https://img.freepik.com/premium-photo/solitary-figure-alien-landscape_441990-40815.jpg",
            "https://img.freepik.com/premium-photo/solar-system-planet-collage_23-2150188980.jpg",
            "https://img.freepik.com/free-photo/3d-rendering-chinese-great-wall_23-2151306943.jpg",
            "https://img.freepik.com/premium-photo/planet-with-rings-around-it_400650-445.jpg",
            "https://img.freepik.com/free-photo/photorealistic-galaxy-background_23-2151064416.jpg",
            "https://i.ytimg.com/vi/mbqmZpaYN94/maxresdefault.jpg",
            "https://img.freepik.com/premium-photo/red-planet-mars-space-with-stars_979187-5236.jpg",
            "https://img.freepik.com/premium-photo/planet-solar-system-universe_967740-3534.jpg",
            "https://img.freepik.com/free-photo/3d-rendering-planet-earth_23-2150498510.jpg",
            "https://img.freepik.com/premium-photo/poster-galaxy-with-girl-standing-front-it_1243200-44132.jpg",
            "https://c8.alamy.com/comp/RMFR3Y/a-solar-eclipse-occurs-when-a-portion-of-the-earth-is-engulfed-in-a-shadow-cast-by-the-moon-which-fully-or-partially-blocks-sunlight-RMFR3Y.jpg"
    };

    private Question[] questionBank = new Question[]{
            new Question(R.string.q1, true, imageUrls[0]),
            new Question(R.string.q2, true, imageUrls[1]),
            new Question(R.string.q3, false, imageUrls[2]),
            new Question(R.string.q4, false, imageUrls[3]),
            new Question(R.string.q5, true, imageUrls[4]),
            new Question(R.string.q6, true, imageUrls[5]),
            new Question(R.string.g, true, imageUrls[6]),
            new Question(R.string.h, true, imageUrls[7]),
            new Question(R.string.i, true, imageUrls[8]),
            new Question(R.string.j, true, imageUrls[9]),
            new Question(R.string.k, true, imageUrls[10]),
            new Question(R.string.l, false, imageUrls[11])
    };

    private DatabaseReference myRef; // Firebase reference for quiz data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("quizData"); // Reference to the root of your database

        // Initialize the UI elements
        falseButton = findViewById(R.id.false_button);
        trueButton = findViewById(R.id.true_button);
        nextButton = findViewById(R.id.next_button);
        prevButton = findViewById(R.id.prev_button);
        questionTextView = findViewById(R.id.answer_text_view);
        image = findViewById(R.id.myimage);
        firstLastQuestionTextView = findViewById(R.id.first_last_question_text_view);

        falseButton.setOnClickListener(this);
        trueButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);

        updateQuestion();

        // Check if the Firebase write works
        Log.d("Firebase", "Firebase Initialized, ready for writing data");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.false_button) {
            checkAnswer(false);
        } else if (v.getId() == R.id.true_button) {
            checkAnswer(true);
        } else if (v.getId() == R.id.next_button) {
            currentQuestionIndex = (currentQuestionIndex + 1) % questionBank.length;
            updateQuestion();
        } else if (v.getId() == R.id.prev_button) {
            currentQuestionIndex = (currentQuestionIndex - 1 + questionBank.length) % questionBank.length;
            updateQuestion();
        }
    }

    private void updateQuestion() {
        questionTextView.setText(questionBank[currentQuestionIndex].getAnswerResId());
        Glide.with(this).load(questionBank[currentQuestionIndex].getImageUrl()).into(image);

        if (currentQuestionIndex == 0) {
            firstLastQuestionTextView.setText("This is the first question.");
        } else if (currentQuestionIndex == questionBank.length - 1) {
            firstLastQuestionTextView.setText("This is the last question.");
        } else {
            firstLastQuestionTextView.setText("");
        }
    }

    private void checkAnswer(boolean userChooseCorrect) {
        boolean answerIsTrue = questionBank[currentQuestionIndex].isAnswerTrue();
        int toastMessageId;

        if (userChooseCorrect == answerIsTrue) {
            toastMessageId = R.string.correct_answer;
            correct++;
        } else {
            toastMessageId = R.string.wrong_answer;
        }

        Toast.makeText(MainActivity.this, toastMessageId, Toast.LENGTH_SHORT).show();

        // Log to check if Firebase update happens
        Log.d("FirebaseUpdate", "Updating score to: " + correct);
        Log.d("FirebaseUpdate", "Updating currentQuestion to: " + currentQuestionIndex);

        // Update data in Firebase using updateChildren()
        Map<String, Object> updates = new HashMap<>();
        updates.put("quiz/score", correct);
        updates.put("quiz/currentQuestion", currentQuestionIndex);
        myRef.updateChildren(updates).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d("FirebaseUpdate", "Successfully updated data!");
            } else {
                Log.e("FirebaseUpdate", "Failed to update data: " + task.getException());
            }
        });

        if (currentQuestionIndex == questionBank.length - 1) {
            // Pass the score to the ThankYouActivity
            Intent intent = new Intent(MainActivity.this, ThankYouActivity.class);
            intent.putExtra("SCORE", correct + "/" + questionBank.length);
            startActivity(intent);
            finish(); // Finish the quiz activity
        }
    }
}
