package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class QuizResult {

    private int resultId;
    private int userId;
    private int quizId;
    private int score;
    private Date attemptDate;

    // Constructor
    public QuizResult(int resultId, int userId, int quizId, int score, Date attemptDate) {
        this.resultId = resultId;
        this.userId = userId;
        this.quizId = quizId;
        this.score = score;
        this.attemptDate = attemptDate;
    }

    // Getters and Setters
    public int getResultId() {
        return resultId;
    }

    public void setResultId(int resultId) {
        this.resultId = resultId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Date getAttemptDate() {
        return attemptDate;
    }

    public void setAttemptDate(Date attemptDate) {
        this.attemptDate = attemptDate;
    }

    // Method to display result details
    public void displayResultDetails() {
        System.out.println("Result ID: " + resultId);
        System.out.println("User ID: " + userId);
        System.out.println("Quiz ID: " + quizId);
        System.out.println("Score: " + score);
        System.out.println("Attempt Date: " + attemptDate);
    }

    // Method to get the date when the quiz was taken
    public String getDateTaken() {
        if (attemptDate != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            return formatter.format(attemptDate);  // Format the date into a readable string
        }
        return null;  // Return null if the attemptDate is not set
    }
}
