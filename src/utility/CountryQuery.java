package utility;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;

import java.lang.module.ResolutionException;
import java.sql.*;
import java.time.LocalDateTime;

/** This class holds methods used to pull information from the database regarding Country records. */
public class CountryQuery {

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
