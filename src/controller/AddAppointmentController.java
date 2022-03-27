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
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AddAppointmentController implements Initializable {


    public TextField titleField;
    public ComboBox contactField;
    public TextField descriptionField;
    public TextField locationField;
    public TextField typeField;
    public ComboBox startTimeComboBox;
    public ComboBox endTimeComboBox;
    public DatePicker datePicker;

    private static User currentUser;
    public ComboBox customerComboBox;
    public Label errorLabel;
    public ComboBox userIdComboBox;


    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        AddAppointmentController.currentUser = currentUser;
    }

/** LAMBDA FUNCTIONS
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
        allContacts.forEach( (c) -> {contactField.getItems().add(c);} );

        // fill each time comboBox with all time options
        ObservableList<LocalTime> localTimes = TimeConversion.createTimeList();
        localTimes.forEach( (t) -> {startTimeComboBox.getItems().add(t);} );
        localTimes.forEach( (t) -> {endTimeComboBox.getItems().add(t);} );

        // get all Customers and set the ID options to the comboBox
        ObservableList<Customer> allCustomers = null;
        try {
            allCustomers = CustomerQuery.getAllCustomers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        allCustomers.forEach( (c) -> {customerComboBox.getItems().add(c.getCustomerId());} );
    }

    public void onActionSaveButtonPressed(ActionEvent actionEvent) throws IOException, SQLException {
        // initialize insert parameters with textfield values
        String title = titleField.getText();
        String description = descriptionField.getText();
        String location = locationField.getText();
        String type = typeField.getText();
        Contact contact = (Contact) contactField.getValue();
        int contactId = contact.getContactId();
        LocalDateTime start = TimeConversion.createLocalDateTime(datePicker.getValue(), (LocalTime) startTimeComboBox.getValue());
        LocalDateTime end = TimeConversion.createLocalDateTime(datePicker.getValue(), (LocalTime) endTimeComboBox.getValue());
        LocalDateTime createDate = LocalDateTime.now(); //use for lastUpdate
        String createdBy = currentUser.getUserName(); //use for lastUpdateBy
        int customerId = (int) customerComboBox.getValue();
        int userId = currentUser.getUserId();

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

    public void onActionCancelButtonPressed(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 600);
        stage.setTitle("Appointments");
        stage.setScene(scene);
        stage.show();
    }
}
