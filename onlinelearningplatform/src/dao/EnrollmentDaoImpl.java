package dao;

import model.Course;
import model.BasicCourse;
import model.AdvancedCourse;
import utility.dbconnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentDaoImpl implements EnrollmentDao {

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
}
