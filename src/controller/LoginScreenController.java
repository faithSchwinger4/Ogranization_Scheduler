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

/** This class launches the LoginScreen and contains functions for user interaction with the screen. */
public class LoginScreenController implements Initializable {

    /** This text-field is where the User enters their username. */
    public TextField usernameField;

    /** This text-field is where the User enters their password. */
    public TextField passwordField;

    /** This label is where the current Timezone is displayed. */
    public Label localTimeZoneLabel;

    /** This label is where the invalid user message is displayed when an invalid username and password combination is
     * entered. */
    public Label invalidUserLabel;

    /** This label displays the phrase "User Login" above the entry fields in either French or English depending on the
     * computer's location. */
    public Label userLoginLabel;

    /** This label displays the word "Username" above the corresponding text-field in either French or English depending
     * on the computer's language. */
    public Label usernameLabel;

    /** This label displays the word "Password" above the corresponding text-field in either French or English depending
     * on the computer's language. */
    public Label passwordLabel;

    /** This button is pressed to attempt login. It displays the word "Login" in French or English depending on the
     * user's computer language settings. */
    public Button loginButton;

    /** This method initializes all the labels on the screen based on the computer's language settings using the
     * default locale and a resourceBundle. */
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

    /** When the login button is pressed, this method gets the entered username and password from the fields and checks
     * if it's a valid combination in the database with a query.
     * <p>If valid, the user is granted access to the rest of the
     *      * application. A confirmation alert will also pop up indicating whether or not an appointment is set to
     *      begin within 15 minutes of the successful login. If it is, then appointment details are displayed as well.</p>
     * <p>If it's invalid the user is given an error message stating the combination was invalid in either
     *      * French or English depending on their computer's language setting. </p>
     * <p>The login attempt is then logged and appended to a login_activity text file as being either successful or unsuccessful
     *      * with the attempted username, password, and the date and time of the attempt.</p> */
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
