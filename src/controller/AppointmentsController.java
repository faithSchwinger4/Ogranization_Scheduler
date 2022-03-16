package controller;

import helper.AppointmentQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class AppointmentsController implements Initializable {
    public Label totalAppointmentCount; //where the count of appointments by type and month is output

    @FXML
    private TableView<Appointment> appointmentTable;
    @FXML
    private TableColumn<Appointment, Integer> appointmentId;
    @FXML
    private TableColumn<Appointment, String> title;
    @FXML
    private TableColumn<Appointment, String> description;
    @FXML
    private TableColumn<Appointment, String> location;
    @FXML
    private TableColumn<Appointment, String> contact;
    @FXML
    private TableColumn<Appointment, String> type;
    @FXML
    private TableColumn<Appointment, Timestamp> startDateAndTime;
    @FXML
    private TableColumn<Appointment, Timestamp> endDateAndTime;
    @FXML
    private TableColumn<Appointment, Integer> customerId;
    @FXML
    private TableColumn<Appointment, Integer> userId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // get observable list of all appointments
        try {
            appointmentTable.setItems(AppointmentQuery.getAllAppointments());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //fill each column
        appointmentId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        location.setCellValueFactory(new PropertyValueFactory<>("location"));
        contact.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDateAndTime.setCellValueFactory(new PropertyValueFactory<>("start")); ;
        endDateAndTime.setCellValueFactory(new PropertyValueFactory<>("end")); ;
        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId")); ;
        userId.setCellValueFactory(new PropertyValueFactory<>("userId")); ;
    }

    public void onActionReturnToMainMenuButtonPressed(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 400, 500);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }

    public void onActionAddAppointment(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddAppointment.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 400, 550);
        stage.setTitle("Add Appointment");
        stage.setScene(scene);
        stage.show();
    }

    public void onActionUpdateAppointment(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/UpdateAppointment.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 400, 550);
        stage.setTitle("Update Appointment");
        stage.setScene(scene);
        stage.show();
    }
}
