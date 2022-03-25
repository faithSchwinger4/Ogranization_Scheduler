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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;
import utility.AppointmentQuery;
import utility.ContactQuery;
import utility.CustomerQuery;
import utility.TimeConversion;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class UpdateAppointmentController implements Initializable {

    private static User currentUser;
    private static Appointment appointment;
    public TextField titleField;
    public TextField appointmentIdField;
    public ComboBox contactComboBox;
    public DatePicker datePicker;
    public TextField descriptionField;
    public TextField locationField;
    public TextField typeField;
    public ComboBox<LocalTime> startTimeComboBox;
    public ComboBox<LocalTime> endTimeComboBox;
    public ComboBox customerIdComboBox;


/** LAMBDA FUNCTIONS
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
        allContacts.forEach( (c) -> {contactComboBox.getItems().add(c);} );

        //choose which contact automatically displayed
        for(Contact contact : allContacts) {
            if (appointment.getContactId() == contact.getContactId()) {
                contactComboBox.setValue(contact);
            }
        }

        datePicker.setValue(appointment.getStart().toLocalDate());

        // fill each time comboBox with all time options
        ObservableList<LocalTime> localTimes = TimeConversion.createTimeList();
        localTimes.forEach( (t) -> {startTimeComboBox.getItems().add(t);} );
        localTimes.forEach( (t) -> {endTimeComboBox.getItems().add(t);} );

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
        allCustomers.forEach( (c) -> {customerIdComboBox.getItems().add(c);} );

        //initialize the chosen customerID
        customerIdComboBox.setValue(appointment.getCustomerId());
    }


    public void onActionCancelButtonPressed(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 600);
        stage.setTitle("Appointments");
        stage.setScene(scene);
        stage.show();
    }

    public void onActionSaveButtonPressed(ActionEvent actionEvent) throws IOException, SQLException {
        String title = titleField.getText();
        String description = descriptionField.getText();
        String location = locationField.getText();
        String type = typeField.getText();
        Contact contact = (Contact) contactComboBox.getValue();
        int contactId = contact.getContactId();
        LocalDateTime start = TimeConversion.createLocalDateTime(datePicker.getValue(), (LocalTime) startTimeComboBox.getValue());
        LocalDateTime end = TimeConversion.createLocalDateTime(datePicker.getValue(), (LocalTime) endTimeComboBox.getValue());
        LocalDateTime lastUpdate = LocalDateTime.now(); //use for lastUpdate
        String lastUpdatedBy = currentUser.getUserName(); //use for lastUpdateBy
        int customerId = (int) customerIdComboBox.getValue();
        int userId = currentUser.getUserId();
        int appointmentId = appointment.getAppointmentId();

        AppointmentQuery.update(appointmentId, title, description, location, type, Timestamp.valueOf(start), Timestamp.valueOf(end),
                Timestamp.valueOf(lastUpdate), lastUpdatedBy, customerId, contactId, userId);

        Parent root = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 600);
        stage.setTitle("Appointments");
        stage.setScene(scene);
        stage.show();
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        UpdateAppointmentController.currentUser = currentUser;
    }

    public static Appointment getAppointment() {
        return appointment;
    }

    public static void setAppointment(Appointment appointment) {
        UpdateAppointmentController.appointment = appointment;
    }
}
