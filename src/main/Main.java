package main;

import utility.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

/** This class launches the application and calls up the first Login Screen to be displayed to the user. */
public class Main extends Application {

    /** This method overrides the start method to call up the first Login Screen. */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginScreen.fxml"));

        ResourceBundle rb = ResourceBundle.getBundle("lang/Nat", Locale.getDefault());

        stage.setTitle(rb.getString("Login") + " " + rb.getString("Screen"));
        stage.setScene(new Scene(root,600,400));
        stage.show();
    }

    /** This method connects to the database, launches the application, and then closes the connection to the database
     * once the application is closed. */
    public static void main (String[] args) throws SQLException {

        JDBC.openConnection();
        launch(args); //startup your GUIs
        JDBC.closeConnection();
    }
}
