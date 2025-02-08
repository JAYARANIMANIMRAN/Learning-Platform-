package model;

public class BasicCourse extends Course {

    public BasicCourse(int courseId, String courseName, String description, int duration) {
        super(courseId, courseName, description,"Basic", duration);
    }

    @Override
    public void getCourseDetails() {
        System.out.println("This is a Basic Level Course.");
        // You can add more details specific to BasicCourse here
    }
}
