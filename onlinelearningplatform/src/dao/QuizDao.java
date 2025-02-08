package dao;

import model.Quiz;
import model.QuizResult;

import java.sql.SQLException;
import java.util.List;

import exception.QuizNotFoundException;

public interface QuizDao {
	
    Quiz getQuizById(int quizId) throws SQLException, QuizNotFoundException;

    List<Quiz> getQuizzesByCourseId(int courseId) throws SQLException;

    List<Quiz> getUserQuizzes(int userId) throws SQLException;

	void submitQuizResult(int userId, int quizId, int score) throws SQLException;
	
	 public int getLessonsCompleted(int userId, int courseId) throws SQLException;

	List<QuizResult> getQuizResultsByUserId(int userId) throws SQLException;

	List<Quiz> getAllQuizzes() throws SQLException;
}
