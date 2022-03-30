package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;
import utility.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

/** This class launches the UpdateAppointment screen and contains functions for user interaction with the screen. */
public class UpdateAppointmentController implements Initializable {

    /** Stores the current user's information as a User object. This allows the Appointment screen to pass through the
     * current user's information. */
    private static User currentUser;

    /** This stores the appointment object the user chose to update in the Appointments screen. It is used to initialize
     * all the fields on this screen. */
    private static Appointment appointment;

    /** The field for the updated appointment title to be input. */
    public TextField titleField;

    /** The field for the appointment ID to be displayed that doesn't allow the user to edit it. */
    public TextField appointmentIdField;

    /** The combo box for the updated contact to be chosen. */
    public ComboBox contactComboBox;

    /** The date picker for the updated appointment date to be chosen. */
    public DatePicker datePicker;

    /** The field for the updated appointment description to be input. */
    public TextField descriptionField;

    /** The field for the updated appointment location to be input. */
    public TextField locationField;

    /** The field for the updated appointment type to be input. */
    public TextField typeField;

    /** The combo box for the updated appointment start time to be chosen. */
    public ComboBox<LocalTime> startTimeComboBox;

    /** The combo box for the updated appointment end time to be chosen. */
    public ComboBox<LocalTime> endTimeComboBox;

    /** The combo box for the updated appointment customer ID to be chosen. */
    public ComboBox customerIdComboBox;

    /** The label where any errors are printed out to. Here the user is made aware of any invalid start or end times and
     * the reason why they're invalid. */
    public Label errorLabel;

    /** The combo box for the updated appointment user ID to be chosen. */
    public ComboBox<User> userIdComboBox;


    /** LAMBDA This function initializes all the comboBoxes on the screen including the ones for contacts, appointment start times,
     * appointment end times, customer IDs, and user IDs with the appropriate options. This prevents the user from adding
     * an appointment with the incorrect contact, customer, or user ID that would prevent it from being added to the database.
     * It also loads in the data from the selected appointment into each field on the page, including selecting the
     * correct choice from each comboBox.
     * <p>
     *     LAMBDA FUNCTIONS
     *     There are 5 lambda functions in this method. Each one utilizes a .forEach appended to an ObservableList with a
     *     lambda function specifying that each item in the list is to be added to a specific comboBox. This made my code
     *     much simpler and cleaner.
     * </p>
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentIdField.setText(Integer.toString(appointment.getAppointmentId()));
        titleField.setText(appointment.getTitle());
        descriptionField.setText(appointment.getDescription());
        locationField.setText(appointment.getLocation());
        typeField.setText(appointment.getType());

        // fill the contact combobox
        ObservableList<Contact> allContacts = FXCollections.observableArrayList();
        try {
            allContacts = ContactQuery.getAllContacts();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        allContacts.forEach((c) -> {
            contactComboBox.getItems().add(c);
        });

        //choose which contact automatically displayed
        for (Contact contact : allContacts) {
            if (appointment.getContactId() == contact.getContactId()) {
                contactComboBox.setValue(contact);
            }
        }

        datePicker.setValue(appointment.getStart().toLocalDate());

        // fill each time comboBox with all time options
        ObservableList<LocalTime> localTimes = TimeHelper.createTimeList();
        localTimes.forEach((t) -> {
            startTimeComboBox.getItems().add(t);
        });
        localTimes.forEach((t) -> {
            endTimeComboBox.getItems().add(t);
        });

        //set the time loaded from the appointment
        startTimeComboBox.setValue(appointment.getStart().toLocalTime());
        endTimeComboBox.setValue(appointment.getEnd().toLocalTime());

        // create customerId combobox list
        ObservableList<Customer> allCustomers = null;
        try {
            allCustomers = CustomerQuery.getAllCustomers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        allCustomers.forEach((c) -> {
            customerIdComboBox.getItems().add(c.getCustomerId());
        });

        //initialize the original customerID
        customerIdComboBox.setValue(appointment.getCustomerId());

        // User Id options into comboBox
        ObservableList<User> allUsers = null;
        try {
            allUsers = UserQuery.getAllUsers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        allUsers.forEach((u) -> userIdComboBox.getItems().add(u));

        //initialize the original userId
        User user = null;
        try {
            user = UserQuery.getUser(appointment.getUserId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        userIdComboBox.setValue(user);
    }

    /** This method will return the user to the Appointments screen without updating any existing appointment data. */
    public void onActionCancelButtonPressed(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 600);
        stage.setTitle("Appointments");
        stage.setScene(scene);
        stage.show();
    }

    /** When the save button is pressed, this method will validate the input appointment start and end date and times.
     * If the times don't conflict with a customer's previous appointment, and they are inside business hours, then
     * the information from all the fields is used to update the existing appointment in the database with a query using
     * the appointment ID.
     * It will then redirect the user to the Appointments screen. */
    public void onActionSaveButtonPressed(ActionEvent actionEvent) throws IOException, SQLException {
        String title = titleField.getText();
        String description = descriptionField.getText();
        String location = locationField.getText();
        String type = typeField.getText();
        Contact contact = (Contact) contactComboBox.getValue();
        int contactId = contact.getContactId();
        LocalDateTime start = TimeHelper.createLocalDateTime(datePicker.getValue(), (LocalTime) startTimeComboBox.getValue());
        LocalDateTime end = TimeHelper.createLocalDateTime(datePicker.getValue(), (LocalTime) endTimeComboBox.getValue());
        LocalDateTime lastUpdate = LocalDateTime.now(); //use for lastUpdate
        String lastUpdatedBy = currentUser.getUserName(); //use for lastUpdateBy
        int customerId = (int) customerIdComboBox.getValue();
        int userId = userIdComboBox.getValue().getUserId();
        int appointmentId = appointment.getAppointmentId();

        //check if the appointment time is valid for the customer
        Boolean noConflictingAppointments = AppointmentTimeValidation.noConflictingAppointment(appointment, customerId, start, end);
        Boolean duringBusinessHours = AppointmentTimeValidation.duringBusinessHours(start, end);


        if (noConflictingAppointments && duringBusinessHours) {
            AppointmentQuery.update(appointmentId, title, description, location, type, Timestamp.valueOf(start), Timestamp.valueOf(end),
                    Timestamp.valueOf(lastUpdate), lastUpdatedBy, customerId, userId, contactId);

            Parent root = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 600);
            stage.setTitle("Appointments");
            stage.setScene(scene);
            stage.show();
        }
        else if (!noConflictingAppointments && !duringBusinessHours) {
            errorLabel.setText("Error: Customer has existing appointment at this time. \nError: Appointment outside " +
                    "business hours.");
        }
        else if (!noConflictingAppointments) {
            errorLabel.setText("Error: Customer has existing appointment at this time.");
        }
        else {
            errorLabel.setText("Error: Appointment outside business hours.");
        }
    }

    /** This function sets the current user information. It allows the previous screen to pass through the information
     * for the current user.
     * @param currentUser is the User object representing the current user logged into the application. */
    public static void setCurrentUser(User currentUser) {
        UpdateAppointmentController.currentUser = currentUser;
    }

    /** This function gets the current appointment object that is being updated.
     * @return returns the current appointment object being updated */
    public static Appointment getAppointment() {
        return appointment;
    }

    /** This function sets the appointment that the user wants to update.
     * @param appointment is the appointment object used to initialize the fields on the update appointment screen */
    public static void setAppointment(Appointment appointment) {
        UpdateAppointmentController.appointment = appointment;
    }
}
