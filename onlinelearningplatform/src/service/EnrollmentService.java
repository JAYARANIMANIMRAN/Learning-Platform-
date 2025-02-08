package service;

import dao.EnrollmentDao;
import dao.EnrollmentDaoImpl;
import model.Course;

import java.sql.SQLException;
import java.util.List;

public class EnrollmentService {
    private final EnrollmentDao enrollmentDao;

    public EnrollmentService() {
        this.enrollmentDao = new EnrollmentDaoImpl();
    }

    // Enrolls a user in a specified course
    public void enrollUserInCourse(int userId, int courseId) throws SQLException {
        enrollmentDao.enrollUserInCourse(userId, courseId);
    }

    // Gets all courses a user is enrolled in
    public List<Course> getUserEnrollments(int userId) throws SQLException {
        return enrollmentDao.getUserCourses(userId);
    }
}
