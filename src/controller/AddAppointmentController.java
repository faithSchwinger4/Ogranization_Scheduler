package controller;

import model.User;
import utility.AppointmentQuery;
import utility.ContactQuery;
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
import utility.TimeConversion;

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
    public TextField customerIdField;
    public ComboBox startTimeComboBox;
    public ComboBox endTimeComboBox;
    public DatePicker datePicker;

    private static User currentUser;


    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        AddAppointmentController.currentUser = currentUser;
    }

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

        ObservableList<LocalTime> localTimes = TimeConversion.createTimeList();
        for (LocalTime time : localTimes) {
            startTimeComboBox.getItems().add(time);
        }
        for (LocalTime time : localTimes) {
            endTimeComboBox.getItems().add(time);
        }

    }

    public void onActionSaveButtonPressed(ActionEvent actionEvent) throws IOException, SQLException {
        // initialize insert parameters with textfield values
        String title = titleField.getText();
        String description = descriptionField.getText();
        String location = locationField.getText();
        String type = typeField.getText();
        String contactName = (String) contactField.getValue();
        int contactId = ContactQuery.findContactId(contactName);
        LocalDateTime start = TimeConversion.createLocalDateTime(datePicker.getValue(), (LocalTime) startTimeComboBox.getValue());
        LocalDateTime end = TimeConversion.createLocalDateTime(datePicker.getValue(), (LocalTime) endTimeComboBox.getValue());
        LocalDateTime createDate = LocalDateTime.now(); //use for lastUpdate
        String createdBy = currentUser.getUserName(); //use for lastUpdateBy
        int customerId = Integer.parseInt(customerIdField.getText());
        int userId = currentUser.getUserId();


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

    public void onActionCancelButtonPressed(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 600);
        stage.setTitle("Appointments");
        stage.setScene(scene);
        stage.show();
    }
}
