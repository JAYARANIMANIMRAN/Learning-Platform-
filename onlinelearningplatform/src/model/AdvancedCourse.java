package model;

public class AdvancedCourse extends Course {

    public AdvancedCourse(int courseId, String courseName, String description, int duration) {
        super(courseId, courseName, description,"Advance", duration);
    }

    @Override
    public void getCourseDetails() {
        System.out.println("This is an Advanced Level Course.");
        // You can add more details specific to AdvancedCourse here
    }
}
