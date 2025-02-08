package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;  // Assuming we use a list of results


import dao.QuizDao;
import dao.QuizDaoImpl;
import exception.QuizNotFoundException;
import model.Question;
import model.Quiz;
import model.QuizResult;  // Assuming there's a QuizResult class to hold the result data
import utility.dbconnect;

public class QuizService {

    private final QuizDao quizDao;

    public QuizService() {
        this.quizDao = new QuizDaoImpl();
    }

    // Get quiz by quiz ID
    public Quiz getQuizById(int quizId) throws SQLException, QuizNotFoundException {
        return quizDao.getQuizById(quizId);
    }

    // Get quizzes for a course
    public void getQuizzesByCourseId(int courseId) throws SQLException {
        quizDao.getQuizzesByCourseId(courseId);
    }

    // User taking the quiz
    public void takeQuiz(int userId, int quizId, int score) throws SQLException {
        quizDao.submitQuizResult(userId, quizId, score);
        System.out.println("Quiz taken and result saved.");
    }

    // Submit quiz result for a user with a provided score
    public void submitQuiz(int userId, int quizid, int score) throws SQLException {
        int quizId = quizid;
        quizDao.submitQuizResult(userId, quizId, score);
        System.out.println("Quiz submitted successfully with score: " + score);
    }

    // Get quiz results for a user
    public List<QuizResult> getQuizResults(int userId) throws SQLException {
        // Fetching the quiz results from the QuizDao (assuming such a method exists)
        List<QuizResult> results = quizDao.getQuizResultsByUserId(userId);
        if (results == null || results.isEmpty()) {
            System.out.println("No quiz results found for the user.");
        }
        return results;
    }
    public List<Quiz> getAllQuizzes() throws SQLException {
        // Assuming quizDao is a DAO that interacts with the database
        return quizDao.getAllQuizzes();
    }

 // Method to get questions for a quiz
    public List<Question> getQuestionsForQuiz(int quizId) throws SQLException {
        // SQL query to fetch questions related to a specific quiz
        String sql = "SELECT * FROM Questions WHERE quiz_id = ?";
        List<Question> questions = new ArrayList<>();

        // Establishing the connection to the database
        try (Connection con = dbconnect.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // Setting the quizId parameter for the query
            ps.setInt(1, quizId);

            // Executing the query
            ResultSet rs = ps.executeQuery();

            // Iterating over the result set to create Question objects
            while (rs.next()) {
                Question question = new Question(
                    rs.getInt("question_id"),
                    rs.getInt("quiz_id"),
                    rs.getString("question_text"),
                    rs.getString("option_a"),
                    rs.getString("option_b"),
                    rs.getString("option_c"),
                    rs.getString("option_d"),
                    rs.getString("correct_option").charAt(0) // Extracting the correct option
                );
                // Adding the question to the list
                questions.add(question);
            }
        }

        // Returning the list of questions for the quiz
        return questions;
    }

}
