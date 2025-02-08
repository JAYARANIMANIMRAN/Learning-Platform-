package dao;

import model.Course;
import exception.CourseNotFoundException;

import java.sql.SQLException;
import java.util.List;

public interface CourseDao {
    
    Course getCourseById(int courseId) throws SQLException, CourseNotFoundException;
  
    List<Course> getAllCourses() throws SQLException;

    void enrollUserInCourse(int userId, int courseId) throws SQLException;

    List<Course> getUserCourses(int userId) throws SQLException;

	boolean checkUserEnrollment(int userId, int courseId) throws SQLException;

	int getCompletedQuizzesCount(int userId, int courseId) throws SQLException;

	int getCompletedLessonsCount(int userId, int courseId) throws SQLException;

	List<Course> getCoursesByType(int courseType) throws SQLException;

	
}
