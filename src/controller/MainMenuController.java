package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;

/** This class launches the MainMenu screen and contains functions for user interaction with the screen. */
public class MainMenuController {

    /** Stores the current user's information as a User object. This allows the Appointment screen to pass through the
     * current user's information. */
    private static User currentUser;

    /** This function sets the current user information. It allows the previous screen to pass through the information
     * for the current user.
     * @param currentUser is the User object representing the current user logged into the application */
    public static void setCurrentUser(User currentUser) {
        MainMenuController.currentUser = currentUser;
    }

    /** This method launches the Customers screen when the "View Customers" button is pressed. */
    public void onActionCustomersButtonPressed(ActionEvent actionEvent) throws IOException {
        CustomersController.setCurrentUser(currentUser);

        Parent root = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 500);
        stage.setTitle("Customers");
        stage.setScene(scene);
        stage.show();
    }

    /** This method launches the Appointments screen when the "View Appointments" button is pressed. */
    public void onActionAppointmentsButtonPressed(ActionEvent actionEvent) throws IOException {
        AppointmentsController.setCurrentUser(currentUser);

        Parent root = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 600);
        stage.setTitle("Appointments");
        stage.setScene(scene);
        stage.show();
    }

    /** This method launches the ContactSchedules screen when the "Contact Schedules" button is pressed. */
    public void onActionSchedulesButtonPressed(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Schedules.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Schedules");
        stage.setScene(scene);
        stage.show();
    }

    /** This method launches the CustomerReports screen when the "Customer Reports" button is pressed. */
    public void onCustomerReportsButtonPressed(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerReports.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 650, 500);
        stage.setTitle("Schedules");
        stage.setScene(scene);
        stage.show();
    }
}
