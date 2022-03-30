package utility;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This class holds methods used to pull information from the database regarding Contact records. */
public class ContactQuery {

    /**  */
    public static ObservableList<Contact> getAllContacts() throws SQLException {
        ObservableList<Contact> allContacts = FXCollections.observableArrayList();

        String sql = "SELECT Contact_ID, Contact_Name, Email FROM Contacts";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int contactId = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            String email = rs.getString("Email");
            Contact nextContact = new Contact(contactId, contactName, email);
            allContacts.add(nextContact);
        }
        return allContacts;
    }

    public static int findContactId(String contactName) throws SQLException {
        String sql = "SELECT Contact_ID FROM Contacts WHERE Contact_Name = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, contactName);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt("Contact_ID");
    }
}
