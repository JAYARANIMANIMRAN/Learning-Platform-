package dao;

import model.Course;
import model.BasicCourse;
import model.AdvancedCourse;
import exception.CourseNotFoundException;
import utility.dbconnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDaoImpl implements CourseDao {

    // Method to get course details by courseId
    @Override
    public Course getCourseById(int courseId) throws SQLException, CourseNotFoundException {
        String sql = "SELECT * FROM Courses WHERE course_id = ?";

        try (Connection con = dbconnect.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, courseId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Create the correct course subclass based on the course level
                String level = rs.getString("level");
                Course course;

                if ("Basic".equalsIgnoreCase(level)) {
                    course = new BasicCourse(rs.getInt("course_id"), rs.getString("course_name"),
                            rs.getString("description"), rs.getInt("duration"));
                } else if ("Advanced".equalsIgnoreCase(level)) {
                    course = new AdvancedCourse(rs.getInt("course_id"), rs.getString("course_name"),
                            rs.getString("description"), rs.getInt("duration"));
                } else {
                    throw new CourseNotFoundException("Unknown course level: " + level);
                }

                return course;
            } else {
                throw new CourseNotFoundException("Course with ID " + courseId + " not found.");
            }
        }
    }

    // Method to get all courses
    @Override
    public List<Course> getAllCourses() throws SQLException {
        String sql = "SELECT * FROM Courses";
        List<Course> courses = new ArrayList<>();

        try (Connection con = dbconnect.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // Create the correct course subclass based on the course level
                String level = rs.getString("level");
                Course course;

                if ("Basic".equalsIgnoreCase(level)) {
                    course = new BasicCourse(rs.getInt("course_id"), rs.getString("course_name"),
                            rs.getString("description"), rs.getInt("duration"));
                } else if ("Advanced".equalsIgnoreCase(level)) {
                    course = new AdvancedCourse(rs.getInt("course_id"), rs.getString("course_name"),
                            rs.getString("description"), rs.getInt("duration"));
                } else {
                    continue; // Skip if the level is unknown
                }

                courses.add(course);
            }
        }
        return courses;
    }

    // Method to enroll a user in a course
    @Override
    public void enrollUserInCourse(int userId, int courseId) throws SQLException {
        String sql = "INSERT INTO Enrollments (user_id, course_id, enroll_date, completion_status) VALUES (?, ?, CURDATE(), 'In Progress')";

        try (Connection con = dbconnect.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, courseId);

            int result = ps.executeUpdate();
            if (result > 0) {
                System.out.println("User successfully enrolled in the course.");
            } else {
                System.out.println("Enrollment failed.");
            }
        }
    }

    // Method to get all courses a user is enrolled in
    @Override
    public List<Course> getUserCourses(int userId) throws SQLException {
        String sql = "SELECT c.course_id, c.course_name, c.description, c.level, c.duration " +
                     "FROM Courses c " +
                     "JOIN Enrollments e ON c.course_id = e.course_id " +
                     "WHERE e.user_id = ?";

        List<Course> courses = new ArrayList<>();

        try (Connection con = dbconnect.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Create the correct course subclass based on the course level
                String level = rs.getString("level");
                Course course;

                if ("Basic".equalsIgnoreCase(level)) {
                    course = new BasicCourse(rs.getInt("course_id"), rs.getString("course_name"),
                            rs.getString("description"), rs.getInt("duration"));
                } else if ("Advanced".equalsIgnoreCase(level)) {
                    course = new AdvancedCourse(rs.getInt("course_id"), rs.getString("course_name"),
                            rs.getString("description"), rs.getInt("duration"));
                } else {
                    continue; // Skip if the level is unknown
                }

                courses.add(course);
            }
        }
        return courses;
    }

    // Method to check if a user is enrolled in a specific course
    @Override
    public boolean checkUserEnrollment(int userId, int courseId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Enrollments WHERE user_id = ? AND course_id = ?";

        try (Connection con = dbconnect.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, courseId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;  // Returns true if the user is enrolled, otherwise false
            }
        }
        return false;  // Return false if there's an issue or no record is found
    }

    // Method to get the number of completed quizzes for a user in a specific course
    @Override
    public int getCompletedQuizzesCount(int userId, int courseId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM QuizResults qr " +
                     "JOIN Quizzes q ON qr.quiz_id = q.quiz_id " +
                     "WHERE q.course_id = ? AND qr.user_id = ?";

        try (Connection con = dbconnect.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, courseId);
            ps.setInt(2, userId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);  // Return the count of completed quizzes
            }
        }
        return 0;  // Return 0 if no quizzes are completed or in case of any errors
    }

    // Method to get the number of completed lessons for a user in a specific course
    @Override
    public int getCompletedLessonsCount(int userId, int courseId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Lessons l " +
                     "JOIN LessonProgress lp ON l.lesson_id = lp.lesson_id " +
                     "WHERE l.course_id = ? AND lp.user_id = ? AND lp.status = 'Completed'";

        try (Connection con = dbconnect.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, courseId);
            ps.setInt(2, userId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);  // Return the count of completed lessons
            }
        }
        return 0;  // Return 0 if no lessons are completed or in case of any errors
    }

    // Method to get courses by their type (Basic or Advanced)
    @Override
    public List<Course> getCoursesByType(int courseType) throws SQLException {
        String sql = "SELECT * FROM Courses WHERE level = ?";
        List<Course> courses = new ArrayList<>();

        try (Connection con = dbconnect.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // "courseType" should map to either 'Basic' or 'Advanced'
            ps.setString(1, courseType == 1 ? "Basic" : "Advanced");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Create the correct course subclass based on the course level
                String level = rs.getString("level");
                Course course;

                if ("Basic".equalsIgnoreCase(level)) {
                    course = new BasicCourse(rs.getInt("course_id"), rs.getString("course_name"),
                            rs.getString("description"), rs.getInt("duration"));
                } else if ("Advanced".equalsIgnoreCase(level)) {
                    course = new AdvancedCourse(rs.getInt("course_id"), rs.getString("course_name"),
                            rs.getString("description"), rs.getInt("duration"));
                } else {
                    continue;  // Skip if the level is unknown
                }

                courses.add(course);
            }
        }
        return courses;
    }
}
