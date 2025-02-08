package dao;

import model.Lesson;

import java.sql.SQLException;
import java.util.List;

public interface LessonDao {

    // Method to get lessons by course ID
    List<Lesson> getLessonsByCourseId(int courseId) throws SQLException;

	Lesson getLessonById(int lessonId) throws SQLException;
}
