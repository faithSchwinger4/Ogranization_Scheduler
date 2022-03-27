package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Appointment;
import model.User;
import utility.AppointmentQuery;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    private static User currentUser;
    public Label appointmentAlertLabel;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        MainMenuController.currentUser = currentUser;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Appointment> allAppointments = null;
        try {
            allAppointments = AppointmentQuery.getAllAppointments();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Appointment possibleUpcomingAppointment = null;

        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime fifteenMinutesLater = currentDateTime.plusMinutes(15);

        for (Appointment appointment : allAppointments) {
            if (appointment.getStart().isAfter(currentDateTime) && appointment.getStart().isBefore(fifteenMinutesLater)) {
                possibleUpcomingAppointment = appointment;
            }
        }

        if (possibleUpcomingAppointment == null) {
            appointmentAlertLabel.setText("There is no upcoming appointment in the next 15 minutes.");
        }
        else {
            appointmentAlertLabel.setText("ALERT: Appointment " + possibleUpcomingAppointment.getAppointmentId() +
                    " starts on " + possibleUpcomingAppointment.getStart().toLocalDate() + " at " +
                    possibleUpcomingAppointment.getStart().toLocalTime() + ".");
        }
    }

    public void onActionCustomersButtonPressed(ActionEvent actionEvent) throws IOException {
        CustomersController.setCurrentUser(currentUser);

        Parent root = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 500);
        stage.setTitle("Customers");
        stage.setScene(scene);
        stage.show();
    }

    public void onActionAppointmentsButtonPressed(ActionEvent actionEvent) throws IOException {
        AppointmentsController.setCurrentUser(currentUser);

        Parent root = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 600);
        stage.setTitle("Appointments");
        stage.setScene(scene);
        stage.show();
    }

    public void onActionSchedulesButtonPressed(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Schedules.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Schedules");
        stage.setScene(scene);
        stage.show();
    }

    public void onCustomerReportsButtonPressed(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerReports.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 650, 500);
        stage.setTitle("Schedules");
        stage.setScene(scene);
        stage.show();
    }
}
