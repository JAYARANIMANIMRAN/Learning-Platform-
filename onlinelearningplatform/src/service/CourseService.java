package service;

import java.sql.SQLException;
import java.util.List;

import dao.CourseDao;
import dao.CourseDaoImpl;
import exception.CourseNotFoundException;
import model.Course;

public class CourseService {

    private final CourseDao courseDao;

    public CourseService() {
        this.courseDao = new CourseDaoImpl();
    }

    // Enroll user in a course
    public void enrollUserInCourse(int userId, int courseId) throws SQLException {
        courseDao.enrollUserInCourse(userId, courseId);
        System.out.println("User successfully enrolled in the course.");
    }

    // Get courses a user is enrolled in
    public void getUserCourses(int userId) throws SQLException {
        courseDao.getUserCourses(userId);
    }

    // Get course details
    public Course getCourseById(int courseId) throws SQLException, CourseNotFoundException {
        return courseDao.getCourseById(courseId);
    }



    // Get the number of quizzes completed by the user in a specific course
    public int getQuizzesCompleted(int userId, int courseId) throws SQLException {
        return courseDao.getCompletedQuizzesCount(userId, courseId);
    }

    // Get the number of lessons completed by the user in a specific course
    public int getLessonsCompleted(int userId, int courseId) throws SQLException {
        return courseDao.getCompletedLessonsCount(userId, courseId);
    }

    // Get courses by type
    public List<Course> getCoursesByType(int courseType) throws SQLException {
        return courseDao.getCoursesByType(courseType);
    }
}
