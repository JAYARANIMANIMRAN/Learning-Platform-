package service;

import java.sql.SQLException;
import dao.UserDao;
import dao.UserDaoImp;
import exception.InvalidCredentialsException;
import exception.UserAlreadyExistsException;
import exception.UserNotFoundException;
import model.User;

public class UserService {

    private final UserDao userDao;
    private User currentUser; // Field to keep track of the logged-in user

    public UserService() {
        this.userDao = new UserDaoImp();
    }

    // Register a new user
    public void registerUser(User user) throws SQLException, UserAlreadyExistsException {
        userDao.createUser(user);
        System.out.println("User registered successfully!");
        this.currentUser = user; // Set the current user after registration
    }

    // Login user by email and password
    public User loginUser(String email, String password) throws SQLException, InvalidCredentialsException, UserNotFoundException {
        User user = userDao.getUserByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("User not found with the given email.");
        }

        // Validate the password
        if (!user.getPassword().equals(password)) {
            throw new InvalidCredentialsException("Invalid password.");
        }

        this.currentUser = user; // Set the current user after successful login
        return user;
    }

    // Get user progress
    public String getUserProgress(int userId) throws SQLException {
        return userDao.getUserProgress(userId);
    }

    // Update user progress
    public void updateUserProgress(int userId, String progress) throws SQLException {
        userDao.updateUserProgress(userId, progress);
        System.out.println("Progress updated successfully.");
    }

    // Get the ID of the current user
    public int getCurrentUserId() {
        if (currentUser != null) {
            return currentUser.getUserId();
        } else {
            throw new IllegalStateException("No user is currently logged in.");
        }
    }
}
