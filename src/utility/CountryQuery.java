package utility;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;

import java.lang.module.ResolutionException;
import java.sql.*;
import java.time.LocalDateTime;

/** This class holds methods used to pull information from the database regarding Country records. */
public class CountryQuery {

    /** This function gets all the countries in the database using a SELECT query. It then creates a Country object
     * from each country record and adds it to an ObservableList returned by the function.
     * @return an ObservableList of every country in the database in the form of Country objects */
    public static ObservableList<Country> getAllCountries() throws SQLException {
        ObservableList<Country> allCountries = FXCollections.observableArrayList();

        String sql = "SELECT Country_ID, Country, Create_Date, Created_By, Last_Update, Last_Updated_By " +
                "FROM Countries";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int countryId = rs.getInt("Country_ID");
            String countryName = rs.getString("Country");
            Timestamp createDate = rs.getTimestamp("Create_Date");
            String createdBy = rs.getString("Created_By");
            LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
            String lastUpdatedBy = rs.getString("Last_Updated_By");

            Country nextCountry = new Country(countryId, countryName, createDate, createdBy, lastUpdate, lastUpdatedBy);
            allCountries.add(nextCountry);
        }

        return allCountries;
    }

    /** This function uses a SELECT query to the database to get a specific country record from the database and create a new
     * Country object with it.
     * @param countryId the ID of the country in the database used to locate it
     * @return the Country object created by the query using the country ID number */
    public static Country getCountryFromID(int countryId) throws SQLException {
        String sql = "SELECT * FROM Countries WHERE Country_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, countryId);
        ResultSet rs = ps.executeQuery();

        rs.next();
        String countryName = rs.getString("Country");
        Timestamp createDate = rs.getTimestamp("Create_Date");
        String createdBy = rs.getString("Created_By");
        LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
        String lastUpdatedBy = rs.getString("Last_Updated_By");

        return new Country(countryId, countryName, createDate, createdBy, lastUpdate, lastUpdatedBy);
    }
}
