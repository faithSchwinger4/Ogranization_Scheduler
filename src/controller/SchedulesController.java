package controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import utility.AppointmentQuery;
import utility.ContactQuery;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/** This class launches the ContactSchedules screen and contains functions for user interaction with the screen. */
public class SchedulesController implements Initializable {

    /** This comboBox is where all the contacts in the database are listed and can be chosen. */
    public ComboBox<Contact> contactComboBox;

    /** This table is where the chosen Contact's appointments are all displayed. */
    @FXML
    private TableView<Appointment> appointmentTable;

    /** This column is where the appointmentIds are listed for each of the contact's appointments. */
    @FXML
    private TableColumn<Appointment, Integer> appointmentIdColumn;

    /** This column is where the titles are listed for each of the contact's appointments. */
    @FXML
    private TableColumn<Appointment, String> titleColumn;

    /** This column is where the descriptions are listed for each of the contact's appointments. */
    @FXML
    private TableColumn<Appointment, String> descriptionColumn;

    /** This column is where the types are listed for each of the contact's appointments. */
    @FXML
    private TableColumn<Appointment, String> typeColumn;

    /** This column is where the start dates and times are listed for each of the contact's appointments. */
    @FXML
    private TableColumn<Appointment, String> startColumn;

    /** This column is where the end dates and times are listed for each of the contact's appointments. */
    @FXML
    private TableColumn<Appointment, String> endColumn;

    /** This column is where the customerIds are listed for each of the contact's appointments. */
    @FXML
    private TableColumn<Appointment, Integer> customerIdColumn;


    /** This method initializes the contactComboBox to contain all the contacts existing in the database using a query
     * and ObservableList. It then sets up the table columns to have the proper cellValueFactories and format the start
     * and end dates and times properly. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Contact> allContacts = null;
        try {
            allContacts = ContactQuery.getAllContacts();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Contact contact : allContacts) {
            contactComboBox.getItems().add(contact);
        }

        //fill each column
        appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        //fill and format time columns
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
        startColumn.setCellValueFactory(appointment ->
                new SimpleStringProperty(appointment.getValue().getStart().format(formatter)));
        endColumn.setCellValueFactory(appointment ->
                new SimpleStringProperty(appointment.getValue().getEnd().format(formatter)));
    }

    /** When a contact is chosen from the contactComboBox, this method uses that Contact's ID and a query to fill the
     * table with only that Contact's appointments. */
    public void onActionContactChosen(ActionEvent actionEvent) throws SQLException {
        int selectedContactId = contactComboBox.getValue().getContactId();
        ObservableList<Appointment> contactAppointments = AppointmentQuery.getContactAppointments(selectedContactId);

        appointmentTable.setItems(contactAppointments);
    }

    /** When the "Main Menu" buttons is pressed, this function sends the user back to the Main Menu screen. */
    public void onActionReturnToMainMenuButtonPressed(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 400, 500);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }
}
