package service;

import dao.LessonDao;
import dao.LessonDaoImpl;
import model.Lesson;

import java.sql.SQLException;
import java.util.List;

public class LessonService {

    private final LessonDao lessonDao;

    public LessonService() {
        this.lessonDao = new LessonDaoImpl();
    }

    // Get all lessons for a course
    public List<Lesson> getLessonsByCourseId(int courseId) throws SQLException {
        return lessonDao.getLessonsByCourseId(courseId);
    }

	    public Lesson getLessonById(int lessonId) throws SQLException {
        return lessonDao.getLessonById(lessonId);  // Call the DAO method
    }

}
