package dao;

import model.Lesson;
import utility.dbconnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LessonDaoImpl implements LessonDao {

    // Method to get lessons by course ID
    @Override
    public List<Lesson> getLessonsByCourseId(int courseId) throws SQLException {
        List<Lesson> lessons = new ArrayList<>();
        String sql = "SELECT * FROM Lessons WHERE course_id = ? ORDER BY or_der";  // ordering by the 'or_der' column

        try (Connection con = dbconnect.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, courseId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Create Lesson object from result set
                Lesson lesson = new Lesson(rs.getInt("lesson_id"), 
                                           rs.getInt("course_id"),
                                           rs.getString("lesson_title"), 
                                           rs.getString("content"), 
                                           rs.getInt("or_der"));
                lessons.add(lesson);
            }
        }
        return lessons;
    }

	@Override
	public Lesson getLessonById(int lessonId) throws SQLException {
        String sql = "SELECT * FROM Lessons WHERE lesson_id = ?";
        Lesson lesson = null;

        try (Connection con = dbconnect.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, lessonId);  // Set the lessonId in the SQL query
            ResultSet rs = ps.executeQuery();

            // If the lesson exists, create a Lesson object with the retrieved data
            if (rs.next()) {
                lesson = new Lesson(
                    rs.getInt("lesson_id"),
                    rs.getInt("course_id"),
                    rs.getString("lesson_title"),
                    rs.getString("content"),
                    rs.getInt("or_der")
                );
            }
        }
        return lesson;  // Return the lesson object or null if not found
    }
}
