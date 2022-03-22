package utility;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public abstract class CustomerQuery {

    public static ObservableList<Customer> getAllCustomers() throws SQLException {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

        String sql = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, Customers.Create_Date, " +
                "Customers.Created_By, Customers.Last_Update, Customers.Last_Updated_By, Customers.Division_ID, " +
                "Division, Countries.Country_ID, Country " +
                "FROM Customers INNER JOIN First_Level_Divisions " +
                "ON Customers.Division_ID = First_Level_Divisions.Division_ID " +
                "INNER JOIN Countries ON First_Level_Divisions.Country_ID = Countries.Country_ID";

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int customerId = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phoneNumber = rs.getString("Phone");
            LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = rs.getString("Created_By");
            LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int divisionId = rs.getInt("Division_ID");
            int countryId = FirstLevelDivisionQuery.getCountryIdFromDivisionId(divisionId);
            String divisionName = rs.getString("Division");
            String countryName = rs.getString("Country");

            Customer nextCustomer = new Customer(customerId, customerName, address, postalCode, phoneNumber, createDate,
                    createdBy, lastUpdate, lastUpdatedBy, divisionId, countryId, divisionName,  countryName);
            allCustomers.add(nextCustomer);
        }

        return allCustomers;
    }

    public static int insert(String customerName, String address, String postalCode, String phone,
                             Timestamp createDate, String createdBy, Timestamp lastUpdate,
                             String lastUpdatedBy, Integer divisionId) throws SQLException {
        //index statement
        String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, " +
                "Last_Update, Last_Updated_By, Division_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)"; //each ? is indexed bind variable

        //prepareStatement
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setTimestamp(5, createDate);
        ps.setString(6, createdBy);
        ps.setTimestamp(7, lastUpdate);
        ps.setString(8, lastUpdatedBy);
        ps.setInt(9, divisionId);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
}
