package dao;

import java.sql.SQLException;

import model.User;
import exception.InvalidCredentialsException;
import exception.UserAlreadyExistsException;
import exception.UserNotFoundException;

public interface UserDao {
   
    public void createUser(User user) throws SQLException, UserAlreadyExistsException;
    
    
    public User getUserByEmail(String email) throws SQLException, InvalidCredentialsException, UserNotFoundException;
    
    
	void updateUserProgress(int userId, String progress) throws SQLException;


	String getUserProgress(int userId) throws SQLException;
}
