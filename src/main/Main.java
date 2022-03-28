package main;

import utility.AppointmentTimeValidation;
import utility.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginScreen.fxml"));

        ResourceBundle rb = ResourceBundle.getBundle("lang/Nat", Locale.getDefault());

        stage.setTitle(rb.getString("Login") + " " + rb.getString("Screen"));
        stage.setScene(new Scene(root,600,400));
        stage.show();
    }

    public static void main (String[] args) throws SQLException {

        JDBC.openConnection();
        launch(args); //startup your GUIs
        JDBC.closeConnection();
    }
}
