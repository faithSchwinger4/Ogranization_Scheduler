package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import utility.UserQuery;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
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

        localTimeZoneLabel.setText("Timezone: " + currentZoneId.toString());

    }

    public void onActionLoginButtonPressed(ActionEvent actionEvent) throws IOException, SQLException {
        // take in username, password
        String username = usernameField.getText();
        String password = passwordField.getText();

        // check db for validity
        User possibleUser = UserQuery.validateUserInfo(username, password);

        // if valid, next screen
        if (possibleUser == null) {
            invalidUserLabel.setText("Error: Invalid username and password combination.\nPlease try again.");
        }
        else {
            MainMenuController.setCurrentUser(possibleUser);

            Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 400, 500);
            stage.setTitle("Main Menu");
            stage.setScene(scene);
            stage.show();
        }
    }
}
