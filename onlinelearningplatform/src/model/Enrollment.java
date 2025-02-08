package model;


public class Enrollment {
 private int enrollmentId;
 private int userId;
 private int courseId;
 private String enrollDate;
 private String completionStatus; 

 // Constructor
 public Enrollment(int enrollmentId, int userId, int courseId, String enrollDate, String completionStatus) {
     this.enrollmentId = enrollmentId;
     this.userId = userId;
     this.courseId = courseId;
     this.enrollDate = enrollDate;
     this.completionStatus = completionStatus;
 }

 // Getters and Setters
 public int getEnrollmentId() {
     return enrollmentId;
 }

 public void setEnrollmentId(int enrollmentId) {
     this.enrollmentId = enrollmentId;
 }

 public int getUserId() {
     return userId;
 }

 public void setUserId(int userId) {
     this.userId = userId;
 }

 public int getCourseId() {
     return courseId;
 }

 public void setCourseId(int courseId) {
     this.courseId = courseId;
 }

 public String getEnrollDate() {
     return enrollDate;
 }

 public void setEnrollDate(String enrollDate) {
     this.enrollDate = enrollDate;
 }

 public String getCompletionStatus() {
     return completionStatus;
 }

 public void setCompletionStatus(String completionStatus) {
     this.completionStatus = completionStatus;
 }


 public void getEnrollmentDetails() {
     System.out.println("Enrollment ID: " + enrollmentId + ", User ID: " + userId + ", Course ID: " + courseId);
     System.out.println("Enroll Date: " + enrollDate + ", Status: " + completionStatus);
 }
}

