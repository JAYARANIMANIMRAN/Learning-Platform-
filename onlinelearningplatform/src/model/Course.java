package model;

public class Course {
    private int courseId;
    protected String courseName;
    private String description;
    private String level; // Basic or Advanced
    private int duration; // Duration in hours

    public Course(int courseId, String courseName, String description, String level, int duration) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.description = description;
        this.level = level;
        this.duration = duration;
    }

    // Getters and setters
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

	public void getCourseDetails() {
		System.out.println("Course Details:");
	    System.out.println("Course Name: " + this.courseName);
	    System.out.println("Description: " + this.description);
	    System.out.println("Level: " + this.level);  // Basic or Advanced
	    System.out.println("Duration: " + this.duration + " hours");
	    System.out.println("-----------------------------------");
		
	}
	class BasicCourse extends Course {
	    public BasicCourse(int courseId, String courseName, String description, int duration) {
	        super(courseId, courseName, description, "Basic", duration);
	    }
	}

	// Advanced Course Class
	class AdvancedCourse extends Course {
	    public AdvancedCourse(int courseId, String courseName, String description, int duration) {
	        super(courseId, courseName, description, "Advanced", duration);
	    }
	}
}
