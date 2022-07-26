package DAO;

import Database.JDBC;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Java class that provides logic for the User DAO
 * @author Adam Rutland-Ruiz
 */
public class UserDAO {

    /**
     * Retrieves the user data from the Users table in the database
     * @return
     */
    public static ObservableList<User> buildUsersList() {
        Connection connect;
        ObservableList<User> users = FXCollections.observableArrayList();
        try {
            connect = JDBC.getConnection();
            String SQL = "SELECT User_ID, User_Name FROM users;";
            ResultSet rs = connect.createStatement().executeQuery(SQL);
            while (rs.next()) {
                User newUser = new User(
                        rs.getInt("User_ID"),
                        rs.getString("User_Name"));
                users.add(newUser);
            }
            return users;
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * Validates the text entered in the login screen by comparing it to the usernames and passwords in the database
     * @param username
     * @param password
     * @return
     * @throws SQLException
     */
    public static boolean validateLogin(String username, String password) throws SQLException {
        String search = "SELECT * FROM users WHERE User_Name=? AND Password=?";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(search);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            return (resultSet.next());
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }
}
