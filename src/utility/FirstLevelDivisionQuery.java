package utility;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FirstLevelDivision;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class FirstLevelDivisionQuery {

    public static ObservableList<FirstLevelDivision> getDivisionsInOneCountry(int countryId) throws SQLException {
        ObservableList<FirstLevelDivision> divisions = FXCollections.observableArrayList();

        String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, countryId);

        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int divisionId = rs.getInt("Division_ID");
            String division = rs.getString("Division");
            LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = rs.getString("Created_By");
            LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
            String lastUpdatedBy = rs.getString("Last_Updated_By");

            FirstLevelDivision nextDivision = new FirstLevelDivision(divisionId, division, createDate, createdBy,
                    lastUpdate, lastUpdatedBy, countryId);
            divisions.add(nextDivision);
        }

        return divisions;
    }

    public static int getCountryIdFromDivisionId(int divisionId) throws SQLException {
        String sql = "SELECT Country_ID FROM First_Level_Divisions WHERE Division_ID = ?";

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, divisionId);
        ResultSet rs = ps.executeQuery();
        rs.next();

        return rs.getInt("Country_ID");
    }
}
