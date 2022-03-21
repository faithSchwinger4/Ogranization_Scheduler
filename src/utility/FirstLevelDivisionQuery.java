package utility;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FirstLevelDivisionQuery {

    public static int getCountryIdFromDivisionId(int divisionId) throws SQLException {
        String sql = "SELECT Country_ID FROM First_Level_Divisions WHERE Division_ID = ?";

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, divisionId);
        ResultSet rs = ps.executeQuery();
        rs.next();

        return rs.getInt("Country_ID");
    }
}
