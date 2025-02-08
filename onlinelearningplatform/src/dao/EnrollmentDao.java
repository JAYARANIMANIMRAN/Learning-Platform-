package dao;

import model.Course;

import java.sql.SQLException;
import java.util.List;

public interface EnrollmentDao {
    
    // Enroll a user in a specific course
    void enrollUserInCourse(int userId, int courseId) throws SQLException;

    // Get all courses a user is enrolled in
    List<Course> getUserCourses(int userId) throws SQLException;
}
