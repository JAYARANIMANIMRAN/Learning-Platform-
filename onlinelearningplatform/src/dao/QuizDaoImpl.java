package dao;

import model.Question;
import model.Quiz;
import model.QuizResult;
import exception.QuizNotFoundException;
import utility.dbconnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuizDaoImpl implements QuizDao {

    // Method to get quiz details by quizId
    @Override
    public Quiz getQuizById(int quizId) throws SQLException, QuizNotFoundException {
        String sql = "SELECT * FROM Quizzes WHERE quiz_id = ?";

        try (Connection con = dbconnect.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, quizId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Quiz(rs.getInt("quiz_id"), rs.getInt("course_id"),
                        rs.getString("quiz_title"), rs.getInt("total_marks"));
            } else {
                throw new QuizNotFoundException("Quiz with ID " + quizId + " not found.");
            }
        }
    }

    // Method to get quizzes by courseId
    @Override
    public List<Quiz> getQuizzesByCourseId(int courseId) throws SQLException {
        String sql = "SELECT * FROM Quizzes WHERE course_id = ?";
        List<Quiz> quizzes = new ArrayList<>();

        try (Connection con = dbconnect.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, courseId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Quiz quiz = new Quiz(rs.getInt("quiz_id"), rs.getInt("course_id"),
                        rs.getString("quiz_title"), rs.getInt("total_marks"));
                quizzes.add(quiz);
            }
        }
        return quizzes;
    }

    // Method to get quizzes taken by a user (using the QuizResults table)
    @Override
    public List<Quiz> getUserQuizzes(int userId) throws SQLException {
        String sql = "SELECT q.quiz_id, q.course_id, q.quiz_title, q.total_marks, q.description " +
                     "FROM Quizzes q " +
                     "JOIN QuizResults qr ON q.quiz_id = qr.quiz_id " +
                     "WHERE qr.user_id = ?";

        List<Quiz> quizzes = new ArrayList<>();

        try (Connection con = dbconnect.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Quiz quiz = new Quiz(rs.getInt("quiz_id"), rs.getInt("course_id"),
                        rs.getString("quiz_title"), rs.getInt("total_marks"));
                quizzes.add(quiz);
            }
        }
        return quizzes;
    }

    // Method to submit a quiz result
    @Override
    public void submitQuizResult(int userId, int quizId, int score) throws SQLException {
        String sql = "INSERT INTO QuizResults (user_id, quiz_id, score, attempt_date) VALUES (?, ?, ?, CURDATE())";

        try (Connection con = dbconnect.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, quizId);
            ps.setInt(3, score);

            int result = ps.executeUpdate();
            if (result > 0) {
                System.out.println("Quiz result submitted successfully!");
            } else {
                System.out.println("Failed to submit quiz result.");
            }
        }
    }

    // Method to get the count of lessons completed by a user for a course
    @Override
    public int getLessonsCompleted(int userId, int courseId) throws SQLException {
        String sql = "SELECT COUNT(DISTINCT l.lesson_id) " +
                     "FROM Lessons l " +
                     "JOIN Enrollments e ON l.course_id = e.course_id " +
                     "WHERE e.user_id = ? AND e.course_id = ? AND e.completion_status = 'Completed'";

        try (Connection con = dbconnect.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, courseId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);  // Returns the count of lessons completed
            }
        }
        return 0;  // Return 0 if no completed lessons found
    }

    // Method to get all quizzes
    public List<Quiz> getAllQuizzes() throws SQLException {
        List<Quiz> quizzes = new ArrayList<>();
        String query = "SELECT * FROM quizzes";  // Assuming the table is named 'quizzes'
        try (Connection conn = dbconnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Quiz quiz = new Quiz(rs.getInt("quiz_id"), rs.getInt("course_id"),
                        rs.getString("quiz_title"), rs.getInt("total_marks"));
                quizzes.add(quiz);
            }
        }
        return quizzes;
    }

    // Method to get quiz results by userId
    @Override
    public List<QuizResult> getQuizResultsByUserId(int userId) throws SQLException {
        String sql = "SELECT result_id, user_id, quiz_id, score, attempt_date FROM QuizResults WHERE user_id = ?";
        List<QuizResult> quizResults = new ArrayList<>();

        try (Connection con = dbconnect.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Correct constructor usage, with the order as resultId, userId, quizId, score, attemptDate
                QuizResult result = new QuizResult(
                        rs.getInt("result_id"),
                        rs.getInt("user_id"),
                        rs.getInt("quiz_id"),
                        rs.getInt("score"),
                        rs.getDate("attempt_date")
                );
                quizResults.add(result);
            }
        }

        return quizResults;
    }
 // Method to get questions for a quiz
    public List<Question> getQuestionsForQuiz(int quizId) throws SQLException {
        String sql = "SELECT * FROM Questions WHERE quiz_id = ?";
        List<Question> questions = new ArrayList<>();

        try (Connection con = dbconnect.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, quizId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Question question = new Question(
                    rs.getInt("question_id"),
                    rs.getInt("quiz_id"),
                    rs.getString("question_text"),
                    rs.getString("option_a"),
                    rs.getString("option_b"),
                    rs.getString("option_c"),
                    rs.getString("option_d"),
                    rs.getString("correct_option").charAt(0)
                );
                questions.add(question);
            }
        }

        return questions;
    }

}
