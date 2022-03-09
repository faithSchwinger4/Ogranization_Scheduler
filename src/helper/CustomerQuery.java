package helper;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public abstract class CustomerQuery {

    public static <DateTime> int insert(String customerName, String address, String postalCode, String phone,
                                        DateTime createDate, String createdBy, Timestamp lastUpdate,
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

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
}
