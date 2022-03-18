package utility;

import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserQuery {

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
