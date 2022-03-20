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
import model.User;
import utility.AppointmentQuery;
import utility.ContactQuery;
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
    public TextField customerIdField;
    public ComboBox<LocalTime> startTimeComboBox;
    public ComboBox<LocalTime> endTimeComboBox;

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
        for(Contact contact : allContacts) {
            contactComboBox.getItems().add(contact);
        }
        //choose which contact automatically displayed
        for(Contact contact : allContacts) {
            if (appointment.getContactId() == contact.getContactId()) {
                contactComboBox.setValue(contact);
            }
        }

        datePicker.setValue(appointment.getStart().toLocalDate());

        //local times combobox list set up
        ObservableList<LocalTime> localTimes = TimeConversion.createTimeList();
        for (LocalTime time : localTimes) {
            startTimeComboBox.getItems().add(time);
            endTimeComboBox.getItems().add(time);
        }

        //set the time loaded from the appointment
        System.out.println(appointment.getStart().toLocalTime().toString());
        System.out.println(appointment.getEnd().toLocalTime().toString());
        for (LocalTime time : startTimeComboBox.getItems()) {
            if (appointment.getStart().toLocalTime() == time) {
                startTimeComboBox.setValue(time);
                System.out.println("Start time found");
            }
        }
        for (LocalTime time : endTimeComboBox.getItems()) {
            if (appointment.getEnd().toLocalTime() == time) {
                endTimeComboBox.setValue(time);
                System.out.println("End time found");
            }
        }

        customerIdField.setText(Integer.toString(appointment.getCustomerId()));
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
        int customerId = Integer.parseInt(customerIdField.getText());
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
