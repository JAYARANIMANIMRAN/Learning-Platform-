package controller;

import java.io.IOException;
import java.sql.SQLException;

import exception.UserNotFoundException;
import exception.EnrollmentException;
import exception.InvalidCredentialsException;
import exception.QuizNotFoundException;
import exception.UserAlreadyExistsException;

public class Main {
    public static void main(String[] args) throws IOException, SQLException, UserNotFoundException, EnrollmentException, NumberFormatException, UserAlreadyExistsException, QuizNotFoundException, InvalidCredentialsException {
        CourseController courseController = new CourseController();
        courseController.start();  // Start the course management system
    }
}
