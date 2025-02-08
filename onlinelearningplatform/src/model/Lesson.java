package model;

public class Lesson {
    private int lessonId;
    private int courseId;
    private String lessonTitle;
    private String content;
    private int orDer;

    public Lesson(int lessonId, int courseId, String lessonTitle, String content, int orDer) {
        this.lessonId = lessonId;
        this.courseId = courseId;
        this.lessonTitle = lessonTitle;
        this.content = content;
        this.orDer = orDer;
    }

    // Getters and Setters
    public int getLessonId() { return lessonId; }
    public void setLessonId(int lessonId) { this.lessonId = lessonId; }

    public int getCourseId() { return courseId; }
    public void setCourseId(int courseId) { this.courseId = courseId; }

    public String getLessonTitle() { return lessonTitle; }
    public void setLessonTitle(String lessonTitle) { this.lessonTitle = lessonTitle; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public int getOrDer() { return orDer; }
    public void setOrDer(int orDer) { this.orDer = orDer; }
}
