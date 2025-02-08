package model;

public class Quiz {
    private int quizId;
    private int courseId;  // References the course this quiz belongs to
    private String quizTitle;
    private int totalMarks;
  

    // Constructor
    public Quiz(int quizId, int courseId, String quizTitle, int totalMarks) {
        super();
        this.quizId = quizId;
        this.courseId = courseId;
        this.quizTitle = quizTitle;
        this.totalMarks = totalMarks;
       
    }

    

    // Getters and Setters
    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getQuizTitle() {
        return quizTitle;
    }

    public void setQuizTitle(String quizTitle) {
        this.quizTitle = quizTitle;
    }

    public int getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(int totalMarks) {
        this.totalMarks = totalMarks;
    }

   

    // Display quiz details (including description)
    public void getQuizDetails() {
        System.out.println("Quiz Title: " + quizTitle + ", Total Marks: " + totalMarks);
      
    }
}
