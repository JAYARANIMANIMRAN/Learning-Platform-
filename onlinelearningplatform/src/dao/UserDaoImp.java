package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import exception.UserNotFoundException;
import model.User;
import utility.dbconnect;

public class UserDaoImp implements UserDao {

    // Register a new user
    @Override
    public void createUser(User user) throws SQLException {
        String sql = "INSERT INTO Users (name, email, password, progress) VALUES (?, ?, ?, ?)";

        try (Connection con = dbconnect.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getProgress());  // Store progress as a JSON string or text

            int result = ps.executeUpdate();
            if (result > 0) {
                System.out.println("User registered successfully.");
            } else {
                System.out.println("Registration failed.");
            }
        }
    }

    // Fetch a user by their email (for login purposes)
    @Override
    public User getUserByEmail(String email) throws SQLException, UserNotFoundException {
        String sql = "SELECT * FROM Users WHERE email = ?";

        try (Connection con = dbconnect.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(rs.getInt("user_id"), rs.getString("name"), rs.getString("email"),
                                rs.getString("password"), rs.getString("progress"));
            } else {
                throw new UserNotFoundException("User not found with email: " + email);
            }
        }
    }

    // Update user's progress in a course
    @Override
    public void updateUserProgress(int userId, String progress) throws SQLException {
        String sql = "UPDATE Users SET progress = ? WHERE user_id = ?";

        try (Connection con = dbconnect.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, progress);
            ps.setInt(2, userId);

            int result = ps.executeUpdate();
            if (result > 0) {
                System.out.println("Progress updated successfully.");
            } else {
                System.out.println("Failed to update progress.");
            }
        }
    }

    // Get the user's progress
    @Override
    public String getUserProgress(int userId) throws SQLException {
        String sql = "SELECT progress FROM Users WHERE user_id = ?";

        try (Connection con = dbconnect.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("progress");
            } else {
                return null; 
            }
        }
    }
}
