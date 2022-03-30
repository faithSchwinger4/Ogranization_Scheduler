package utility;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This class holds methods used to pull information from the database regarding User records. */
public class UserQuery {

    public static ObservableList<User> getAllUsers() throws SQLException {
        ObservableList<User> allUsers = FXCollections.observableArrayList();

        String sql = "SELECT * FROM Users";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            int userId = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            String password = rs.getString("Password");
            allUsers.add(new User(userId, userName, password));
        }

        return allUsers;
    }

    public static User getUser(int userId) throws SQLException {
        String sql = "SELECT * FROM Users WHERE User_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, userId);

        ResultSet rs = ps.executeQuery();
        rs.next();
        String userName = rs.getString("User_Name");
        String password = rs.getString("Password");

        return new User(userId, userName, password);
    }

    public static User validateUserInfo(String trialUserName, String trialPassword) throws SQLException {
        String sql = "SELECT User_ID, User_Name, Password FROM Users WHERE User_Name = ? AND Password = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, trialUserName);
        ps.setString(2, trialPassword);

        ResultSet rs = ps.executeQuery();

        if (rs.next() == false) {
            return null;
        }
        else {
            int userId = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            String password = rs.getString("Password");
            return new User(userId, userName, password);
        }
    }
}
