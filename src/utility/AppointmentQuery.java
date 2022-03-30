package utility;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.*;
import java.time.LocalDateTime;

/** This class holds methods used to pull information from the database regarding appointment records. */
public class AppointmentQuery {

    /** This method uses a SELECT query to the database to get all the appointments currently there. It then reads the
     * result of the query and puts each appointment into an ObservableList that is then returned by the function.
     * @return an observable list of all the appointments in the database */
    public static ObservableList<Appointment> getAllAppointments() throws SQLException {

        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

        String sql = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, " +
                "Last_Update, Last_Updated_By, Customer_ID, User_ID, Appointments.Contact_ID, Contact_Name " +
                "FROM Appointments INNER JOIN Contacts ON Appointments.Contact_ID = Contacts.Contact_ID";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int appointmentId = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
            LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = rs.getString("Created_By");
            LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            int contactId = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            Appointment newAppointment = new Appointment(appointmentId, title, description, location, type, start, end,
                    createDate, createdBy, lastUpdate, lastUpdatedBy, customerId, userId, contactId, contactName);
            allAppointments.add(newAppointment);
        }
        return allAppointments;
    }

    /** This method makes a SELECT query to the database to get all appointments except for the one the user inputs
     * as a parameter.
     * @param appointmentId the appointment ID number that is to be left out of the returned ObservableList
     * @return an ObservableList that gets all appointments in the database except for the one the user wanted to be excluded */
    public static ObservableList<Appointment> getAllAppointmentsExceptChosen(int appointmentId) throws SQLException {

        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

        String sql = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, " +
                "Last_Update, Last_Updated_By, Customer_ID, User_ID, Appointments.Contact_ID, Contact_Name " +
                "FROM Appointments INNER JOIN Contacts ON Appointments.Contact_ID = Contacts.Contact_ID " +
                "WHERE Appointments.Appointment_ID <> ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, appointmentId);

        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int currentAppointmentId = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
            LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = rs.getString("Created_By");
            LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            int contactId = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            Appointment newAppointment = new Appointment(currentAppointmentId, title, description, location, type, start, end,
                    createDate, createdBy, lastUpdate, lastUpdatedBy, customerId, userId, contactId, contactName);
            allAppointments.add(newAppointment);
        }
        return allAppointments;
    }

    /** This function executes a query to the database that inserts a new appointment to the database.
     * It takes as parameters pieces of information for each column in the Appointment table in the database.
     * @param title title of the appointment
     * @param description description of the appointment
     * @param createdBy the user who created the appointment
     * @param contactId the id of the contact associated with the appointment
     * @param userId the id of the user associated with the appointment
     * @param lastUpdate the last date and time the appointment was updated
     * @param lastUpdatedBy the user who last updated the appointment
     * @param createDate the date the appointment was created
     * @param customerId the id of the customer associated with the appointment
     * @param location the location of the appointment
     * @param type the type of the appointment
     * @param start the start date and time of the appointment
     * @param end the end date and time of the appointment
     * @return an integer of the number of rows inserted into the database to verify the appointment was added */
    public static int insert(String title, String description, String location, String type, Timestamp start,
                             Timestamp end, Timestamp createDate, String createdBy, Timestamp lastUpdate,
                             String lastUpdatedBy, int customerId, int userId, int contactId) throws SQLException {

        String sql = "INSERT INTO Appointments (Title, Description, Location, Type, Start, End, Create_Date, " +
                "Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES (?, ?, ?, ?, ?, " +
                "?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2,  description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, start);
        ps.setTimestamp(6, end);
        ps.setTimestamp(7, createDate);
        ps.setString(8, createdBy);
        ps.setTimestamp(9, lastUpdate);
        ps.setString(10, lastUpdatedBy);
        ps.setInt(11, customerId);
        ps.setInt(12, userId);
        ps.setInt(13, contactId);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /** This function uses a customer ID number and SELECT query to get all the appointments from the database
     * for that particular customer.
     * @param customerId the ID of the customer we want the appointments for
     * @return the ObservableList of all the appointmentes a customer has */
    public static ObservableList<Appointment> getCustomerAppointments(int customerId) throws SQLException {
        ObservableList<Appointment> customerAppointments = FXCollections.observableArrayList();

        String sql = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, " +
                "Last_Update, Last_Updated_By, Customer_ID, User_ID, Appointments.Contact_ID, Contact_Name " +
                "FROM Appointments INNER JOIN Contacts ON Appointments.Contact_ID = Contacts.Contact_ID " +
                "WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerId);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int appointmentId = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
            LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = rs.getString("Created_By");
            LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int userId = rs.getInt("User_ID");
            int contactId = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            Appointment newAppointment = new Appointment(appointmentId, title, description, location, type, start, end,
                    createDate, createdBy, lastUpdate, lastUpdatedBy, customerId, userId, contactId, contactName);
            customerAppointments.add(newAppointment);
        }

        return customerAppointments;
    }

    /** This function gets all appointments associated with a particular contact from the database using a SELECT query.
     *  @param contactId the ID number of the contact
     *  @return an ObservableList of all appointments associated with a particular contact */
    public static ObservableList<Appointment> getContactAppointments(int contactId) throws SQLException {
        ObservableList<Appointment> contactAppointments = FXCollections.observableArrayList();

        String sql = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, " +
                "Last_Update, Last_Updated_By, Customer_ID, User_ID, Appointments.Contact_ID, Contact_Name " +
                "FROM Appointments INNER JOIN Contacts ON Appointments.Contact_ID = Contacts.Contact_ID " +
                "WHERE Appointments.Contact_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, contactId);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int appointmentId = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
            LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = rs.getString("Created_By");
            LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int userId = rs.getInt("User_ID");
            int customerId = rs.getInt("Customer_ID");
            String contactName = rs.getString("Contact_Name");
            Appointment newAppointment = new Appointment(appointmentId, title, description, location, type, start, end,
                    createDate, createdBy, lastUpdate, lastUpdatedBy, customerId, userId, contactId, contactName);
            contactAppointments.add(newAppointment);
        }

        return contactAppointments;
    }

    /** This function reads an individual appointment record from the database from the Appointment table and its
     * corresponding information from the Contact table.
     * @param appointmentId the appointment ID number to retrieve a record for
     * @return the Appointment object created from an appointment record in the database, including contact information */
    public static Appointment read(int appointmentId) throws SQLException {
        String sql = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, " +
                "Last_Update, Last_Updated_By, Customer_ID, User_ID, Appointments.Contact_ID, Contact_Name " +
                "FROM Appointments INNER JOIN Contacts ON Appointments.Contact_ID = Contacts.Contact_ID " +
                "WHERE Appointments.Appointment_ID = ?";

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, appointmentId);

        ResultSet rs = ps.executeQuery();
        rs.next();

        String title = rs.getString("Title");
        String description = rs.getString("Description");
        String location = rs.getString("Location");
        String type = rs.getString("Type");
        LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
        LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
        LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
        String createdBy = rs.getString("Created_By");
        LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
        String lastUpdatedBy = rs.getString("Last_Updated_By");
        int userId = rs.getInt("User_ID");
        int customerId = rs.getInt("Customer_ID");
        int contactId = rs.getInt("Contact_ID");
        String contactName = rs.getString("Contact_Name");

        System.out.println("Ran read query");

        return new Appointment(appointmentId, title, description, location, type, start, end,
                createDate, createdBy, lastUpdate, lastUpdatedBy, customerId, userId, contactId, contactName);
    }

    /** This function updates an existing Appointment in the database using an UPDATE query. It has one parameter to
     * identify the existing appointment, and the other parameters contain the updated appointment information.
     * @param appointmentId the appointment ID of the record to be updated, used for identification
     * @param contactId the updated contact ID associated with the appointment
     * @param end the updated end date and time of the appointment
     * @param start the updated start date and time of the appointment
     * @param type the updated type of the appointment
     * @param location the updated location of the appointment
     * @param customerId the updated customer ID associated with the appointment
     * @param lastUpdatedBy the date and time the record is being updated
     * @param userId the updated user ID assigned to the appointment
     * @param lastUpdate the user who is currently updating the appointment
     * @param title the updated title of the appointment
     * @param description the updated description of the appointment
     * @return an integer indicating how many rows were updated, verifying the completion of the database query */
    public static int update(int appointmentId, String title, String description, String location, String type, Timestamp start,
                             Timestamp end, Timestamp lastUpdate, String lastUpdatedBy, int customerId, int userId,
                             int contactId) throws SQLException {

        String sql  = "UPDATE Appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?," +
                "Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE " +
                "Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2,  description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, start);
        ps.setTimestamp(6, end);
        ps.setTimestamp(7, lastUpdate);
        ps.setString(8, lastUpdatedBy);
        ps.setInt(9, customerId);
        ps.setInt(10, userId);
        ps.setInt(11, contactId);
        ps.setInt(12, appointmentId);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }


    /** This function deletes a chosen appointment from the database.
     * @param appointmentId the ID of the appointment to be deleted
     * @return an integer indicating how many rows were deleted, verifying the deletion of the appointment from the database */
    public static int delete(int appointmentId) throws SQLException {
        String sql = "DELETE FROM Appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, appointmentId);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
}
