package controller;

import helper.AppointmentQuery;
import helper.ContactQuery;
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
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ConcurrentModificationException;
import java.util.ResourceBundle;

public class AddAppointmentController implements Initializable {


    public TextField titleField;
    public ComboBox contactField;
    public DatePicker startDatePicker;
    public TextField descriptionField;
    public TextField locationField;
    public TextField typeField;
    public TextField customerIdField;
    public TextField userIdField;
    public TextField endTimeField;
    public TextField startTimeField;
    public DatePicker endDatePicker;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Contact> allContacts = FXCollections.observableArrayList();
        try {
            allContacts = ContactQuery.getAllContacts();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for(Contact contact : allContacts) {
            contactField.getItems().add(contact.getContactName());
        }
    }

    public void onActionSaveButtonPressed(ActionEvent actionEvent) throws IOException {
        // initialize insert parameters with textfield values
        String title = titleField.getText();
        String description = descriptionField.getText();
        String location = locationField.getText();
        String type = typeField.getText();
        String contactName = (String) contactField.getValue();
        //LocalDate startDate = startDatePicker.getValue();
        Timestamp startDateTime = ;

        //AppointmentQuery.insert();



        Parent root = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 600);
        stage.setTitle("Appointments");
        stage.setScene(scene);
        stage.show();
    }

    public Time convertToTime

    public void onActionCancelButtonPressed(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 600);
        stage.setTitle("Appointments");
        stage.setScene(scene);
        stage.show();
    }
}
