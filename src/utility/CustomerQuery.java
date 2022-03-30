package utility;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.*;
import java.time.LocalDateTime;

/** This class holds methods used to pull information from the database regarding Customer records. */
public abstract class CustomerQuery {

    /** This function uses a SELECT query to the database to get all the customers located in the database. Each record
     * returned from the query is used to create a new Customer object which is then added to an ObservableList. This List
     * is returned by the function.
     * @return the ObservableList of all Customer objects created from the database */
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

    /** This function uses an INSERT query to add a new customer record to the database. Each parameter corresponds to
     * a piece of customer information in the database.
     * @param lastUpdate the date and time this customer is being added and therefore updated
     * @param lastUpdatedBy the user who created therefore last updated this customer
     * @param createDate the date and time this customer is being created
     * @param createdBy the user who created this customer
     * @param divisionId the ID number for the division the customer lives in
     * @param customerName the name of the customer
     * @param postalCode the postal code of the customer
     * @param address the address of the customer
     * @param phone the phone number of the customer
     * @return the number of rows returned by the execution of the INSERT query, validating that the insert was successful*/
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

    /** This function updates a customer record in the database using an UPDATE query and information from the parameters.
     * @param address the updated address of the customer
     * @param postalCode the updated postal code of the customer
     * @param customerName the updated name of the customer
     * @param divisionId the updated division ID the customer lives in
     * @param lastUpdatedBy the date and time of the update
     * @param lastUpdate the user making the update
     * @param customerId the ID of the customer being updated
     * @param phoneNumber the updated phone number of the customer
     * @return the number of rows returned by the execution of the DELETE query, validating that the delete was successful*/
    public static int update(int customerId, String customerName, String address, String postalCode, String phoneNumber,
                             Timestamp lastUpdate, String lastUpdatedBy, int divisionId) throws SQLException {

        String sql = "UPDATE Customers SET Customer_Name = ?, Address = ?,  Postal_Code = ?, Phone = ?, Last_Update = ?," +
                " Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phoneNumber);
        ps.setTimestamp(5, lastUpdate);
        ps.setString(6, lastUpdatedBy);
        ps.setInt(7, divisionId);
        ps.setInt(8, customerId);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**  This method executes a DELETE query on the database using the provided Customer ID to delete said customer from
     * the database.
     * @param customerId the ID number of the customer to be deleted
     * @return the number of rows returned by the execution of the DELETE query, validating that the delete was successful*/
    public static int delete(int customerId) throws SQLException {
        String sql = "DELETE FROM Customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerId);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
}
