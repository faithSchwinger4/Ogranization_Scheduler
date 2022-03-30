package utility;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FirstLevelDivision;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/** This class holds methods used to pull information from the database regarding First-Level-Division records. */
public class FirstLevelDivisionQuery {

    /** This function gets all the first-level-divisions in one specific country from the database using a SELECT query
     * and the country ID input as a parameter. It returns all found divisions as FirstLevelDivision objects in an
     * ObservableList.
     * @param countryId the ID of the country for which all first-level-divisions are wanted
     * @return the ObservableList of all FirstLevelDivision objects for a specified country*/
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

    /** This function gets the country ID number from a particular FirstLevelDivision ID number using a SELECT query.
     * @param divisionId the ID number of the first-level-division
     * @return the ID number of the country the division is located in*/
    public static int getCountryIdFromDivisionId(int divisionId) throws SQLException {
        String sql = "SELECT Country_ID FROM First_Level_Divisions WHERE Division_ID = ?";

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, divisionId);
        ResultSet rs = ps.executeQuery();
        rs.next();

        return rs.getInt("Country_ID");
    }

    /** This function uses a FirstLevelDivision ID number to create a FirstLevelDivision object.
     * @param divisionId the ID of the division from which a division object can be created
     * @return the FirstLevelDivision object with the input division ID number*/
    public static FirstLevelDivision getDivisionFromId(int divisionId) throws SQLException {
        String sql = "SELECT * FROM First_Level_Divisions WHERE Division_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, divisionId);
        ResultSet rs = ps.executeQuery();

        rs.next();
        String divisionName = rs.getString("Division");
        LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
        String createdBy = rs.getString("Created_By");
        LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
        String lastUpdatedBy = rs.getString("Last_Updated_By");
        int countryId = rs.getInt("Country_ID");

        return new FirstLevelDivision(divisionId, divisionName, createDate, createdBy, lastUpdate,lastUpdatedBy,
                countryId);
    }
}
