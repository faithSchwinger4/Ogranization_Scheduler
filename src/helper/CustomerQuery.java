package helper;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Time;

public abstract class CustomerQuery {

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
