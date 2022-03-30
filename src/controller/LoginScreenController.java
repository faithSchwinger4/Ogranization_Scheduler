package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.User;
import utility.AppointmentQuery;
import utility.UserQuery;

import java.io.IOException;
import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoginScreenController implements Initializable {

    private static User currentUser;
    public TextField usernameField;
    public TextField passwordField;
    public Label localTimeZoneLabel;
    public Label invalidUserLabel;
    public Label userLoginLabel;
    public Label usernameLabel;
    public Label passwordLabel;
    public Button loginButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //check local time zone, division, determine language to be spoken and translate
        ZoneId currentZoneId = ZoneId.systemDefault();
        ResourceBundle rb = ResourceBundle.getBundle("lang/Nat", Locale.getDefault());

        //set timezone display in proper language
        localTimeZoneLabel.setText(rb.getString("Timezone") + ": " + currentZoneId.toString());

        userLoginLabel.setText(rb.getString("User") + " " + rb.getString("Login"));
        usernameLabel.setText(rb.getString("Username"));
        passwordLabel.setText(rb.getString("Password"));

        loginButton.setText(rb.getString("Login"));

    }

    public void onActionLoginButtonPressed(ActionEvent actionEvent) throws IOException, SQLException {
        // take in username, password
        String username = usernameField.getText();
        String password = passwordField.getText();
        Timestamp loginAttemptTime = Timestamp.valueOf(LocalDateTime.now()  );

        FileWriter fwVariable = new FileWriter("login_activity.txt", true);
        PrintWriter pwVariable = new PrintWriter(fwVariable);

        // check db for validity
        User possibleUser = UserQuery.validateUserInfo(username, password);

        // if valid, next screen
        if (possibleUser == null) {
            ResourceBundle rb = ResourceBundle.getBundle("lang/Nat", Locale.getDefault());

            invalidUserLabel.setText(rb.getString("Error") + ": " + rb.getString("Invalid") + " " +
                    rb.getString("username") + " " + rb.getString("and") + " " + rb.getString("password")
                    + " " + rb.getString("combination") + ".\n" + rb.getString("Please") + " " +
                    rb.getString("try") + " " + rb.getString("again") + ".");

            // record unsuccessful login attempt
            pwVariable.println(username + " " + password + " " + loginAttemptTime + " unsuccessful");
            pwVariable.close();
        }
        else {
            MainMenuController.setCurrentUser(possibleUser);

            // record successful login attempt
            pwVariable.println(username + " " + password + " " + loginAttemptTime + " successful");
            pwVariable.close();

            Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 400, 500);
            stage.setTitle("Main Menu");
            stage.setScene(scene);
            stage.show();

            ResourceBundle rb = ResourceBundle.getBundle("lang/Nat", Locale.getDefault());

            // upcoming appointment alert
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(rb.getString("Appointment") + " " +
                    rb.getString("Alert"));

            // get all appointments
            ObservableList<Appointment> allAppointments = null;
            try {
                allAppointments = AppointmentQuery.getAllAppointments();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // filter through to find if there's an upcoming appointment
            Appointment possibleUpcomingAppointment = null;
            LocalDateTime currentDateTime = LocalDateTime.now();
            LocalDateTime fifteenMinutesLater = currentDateTime.plusMinutes(15);
            for (Appointment appointment : allAppointments) {
                if (appointment.getStart().isAfter(currentDateTime) && appointment.getStart().isBefore(fifteenMinutesLater)) {
                    possibleUpcomingAppointment = appointment;
                }
            }

            // set alert to show if there's an upcoming appointment
            alert.setHeaderText(rb.getString("Upcoming") + " " + rb.getString("appointment") + " " +
                    rb.getString("alert") + ":");
            if (possibleUpcomingAppointment == null) {
                alert.setContentText(rb.getString("There") + " " + rb.getString("is") + " " +
                        rb.getString("no") + " " + rb.getString("upcoming") + " " + rb.getString("appointment")
                        + " " + rb.getString("in") + " " + rb.getString("the") + " " + rb.getString("next")
                        + " 15 " + rb.getString("minutes") + ".");
            }
            else {
                alert.setContentText(rb.getString("ALERT") + ": " + rb.getString("Appointment") + " " +
                        possibleUpcomingAppointment.getAppointmentId() + " " + rb.getString("starts") + " " +
                        rb.getString("on") + " " + possibleUpcomingAppointment.getStart().toLocalDate() + " " +
                        rb.getString("at") + " " + possibleUpcomingAppointment.getStart().toLocalTime() + ".");
            }
            Optional<ButtonType> result = alert.showAndWait();
        }
    }
}
