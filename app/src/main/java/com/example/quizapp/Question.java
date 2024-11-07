package com.example.quizapp;

public class Question {
    private int answerResId;
    private boolean answerTrue;
    private String imageUrl;  // Storing the image URL as a String

    // Constructor for the Question class
    public Question(int answerResId, boolean answerTrue, String imageUrl) {
        this.answerResId = answerResId;
        this.answerTrue = answerTrue;
        this.imageUrl = imageUrl; // Initialize the image URL
    }

    // Getter for the answer resource ID (question text)
    public int getAnswerResId() {
        return answerResId;
    }

    // Getter for the answer's truth value (True/False)
    public boolean isAnswerTrue() {
        return answerTrue;
    }

    // Getter for the image URL (used to load images into an ImageView)
    public String getImageUrl() {
        return imageUrl;
    }
}
