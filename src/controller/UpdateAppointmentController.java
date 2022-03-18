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
import utility.ContactQuery;
import utility.TimeConversion;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
    public ComboBox startTimeComboBox;
    public ComboBox endTimeComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
            contactComboBox.getItems().add(contact.getContactName());
        }
        //choose which contact automatically displayed FIXME


        datePicker.setValue(appointment.getStart().toLocalDate());

        //local times combobox list set up
        ObservableList<LocalTime> localTimes = TimeConversion.createTimeList();
        for (LocalTime time : localTimes) {
            startTimeComboBox.getItems().add(time);
        }
        for (LocalTime time : localTimes) {
            endTimeComboBox.getItems().add(time);
        }
        //set the time loaded from the appointment

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

    public void onActionSaveButtonPressed(ActionEvent actionEvent) throws IOException {



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
