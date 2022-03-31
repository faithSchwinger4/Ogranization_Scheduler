

package controller;

import javafx.scene.control.Label;
import model.Customer;
import model.User;
import utility.*;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Contact;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

/** This class launches the AddAppointment screen and contains functions for user interaction with the screen. */
public class AddAppointmentController implements Initializable {

    /** The field for the new appointment title to be input. */
    public TextField titleField;

    /** The combo box for the contact to be chosen. */
    public ComboBox contactField;

    /** The field for the new appointment description to be input. */
    public TextField descriptionField;

    /** The field for the new appointment location to be input. */
    public TextField locationField;

    /** The field for the new appointment type to be input. */
    public TextField typeField;

    /** The combo box for the new appointment start time to be chosen. */
    public ComboBox startTimeComboBox;

    /** The combo box for the new appointment end time to be chosen. */
    public ComboBox endTimeComboBox;

    /** The date picker for the new appointment date to be chosen. */
    public DatePicker datePicker;

    /** Stores the current user's information as a User object. This allows the Appointment screen to pass through the
     * current user's information. */
    private static User currentUser;

    /** The combo box for the new appointment customer ID to be chosen. */
    public ComboBox customerComboBox;

    /** The label where any errors are printed out to. Here the user is made aware of any invalid start or end times and
     * the reason why they're invalid. */
    public Label errorLabel;

    /** The combo box for the new appointment user ID to be chosen. */
    public ComboBox<User> userIdComboBox;

    /** This function sets the current user information. It allows the previous screen to pass through the information
     * for the current user.
     * @param currentUser is the User object representing the current user logged into the application */
    public static void setCurrentUser(User currentUser) {
        AddAppointmentController.currentUser = currentUser;
    }

    /** LAMBDA This function initializes all the comboBoxes on the screen including the ones for contacts, appointment start times,
     * appointment end times, customer IDs, and user IDs with the appropriate options. This prevents the user from adding
     * an appointment with the incorrect contact, customer, or user ID that would prevent it from being added to the database
     * <p>
     *     LAMBDA FUNCTIONS
     *     There are 5 lambda functions in this method. Each one utilizes a .forEach appended to an ObservableList with a
     *     lambda function specifying that each item in the list is to be added to a specific comboBox. This made my code
     *     much simpler and cleaner.
     * </p>
    * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Contact> allContacts = FXCollections.observableArrayList();
        try {
            allContacts = ContactQuery.getAllContacts();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // fill contact comboBox with all contact options
        allContacts.forEach((c) -> {
            contactField.getItems().add(c);
        });

        // fill each time comboBox with all time options
        ObservableList<LocalTime> localTimes = TimeHelper.createTimeList();
        localTimes.forEach((t) -> {
            startTimeComboBox.getItems().add(t);
        });
        localTimes.forEach((t) -> {
            endTimeComboBox.getItems().add(t);
        });

        // get all Customers and set the ID options to the comboBox
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();;
        try {
            allCustomers = CustomerQuery.getAllCustomers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        allCustomers.forEach((c) -> {
            customerComboBox.getItems().add(c.getCustomerId());
        });

        ObservableList<User> allUsers = FXCollections.observableArrayList();;
        try {
            allUsers = UserQuery.getAllUsers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        allUsers.forEach((u) -> userIdComboBox.getItems().add(u));
    }

    /** When the save button is pressed, this method will validate the input appointment start and end date and times.
     * If the times don't conflict with a customer's previous appointment, and they are inside business hours, then
     * the information from all the fields is used to create a new appointment in the database with a query.
     * It will then redirect the user to the Appointment screen. */
    public void onActionSaveButtonPressed(ActionEvent actionEvent) throws IOException, SQLException {
        // initialize insert parameters with textfield values
        String title = titleField.getText();
        String description = descriptionField.getText();
        String location = locationField.getText();
        String type = typeField.getText();
        Contact contact = (Contact) contactField.getValue();
        int contactId = contact.getContactId();
        LocalDateTime start = TimeHelper.createLocalDateTime(datePicker.getValue(), (LocalTime) startTimeComboBox.getValue());
        LocalDateTime end = TimeHelper.createLocalDateTime(datePicker.getValue(), (LocalTime) endTimeComboBox.getValue());
        LocalDateTime createDate = LocalDateTime.now(); //use for lastUpdate
        String createdBy = currentUser.getUserName(); //use for lastUpdateBy
        int customerId = (int) customerComboBox.getValue();
        int userId = userIdComboBox.getValue().getUserId();

        //check if the appointment time is valid for the customer
        Boolean noConflictingAppointments = AppointmentTimeValidation.noConflictingAppointment(null, customerId, start, end);
        Boolean duringBusinessHours = AppointmentTimeValidation.duringBusinessHours(start, end);

        if (noConflictingAppointments && duringBusinessHours) {
            AppointmentQuery.insert(title, description, location, type, Timestamp.valueOf(start), Timestamp.valueOf(end),
                    Timestamp.valueOf(createDate), createdBy, Timestamp.valueOf(createDate), createdBy, customerId, userId,
                    contactId);

            Parent root = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
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

    /** This method will return the user to the Appointments screen without validating or saving any new appointment
     * data. */
    public void onActionCancelButtonPressed(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 600);
        stage.setTitle("Appointments");
        stage.setScene(scene);
        stage.show();
    }
}
