package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginScreen.fxml"));
        stage.setTitle("Login Screen");
        stage.setScene(new Scene(root,600,400));
        stage.show();
    }

    public static void main (String[] args){
        launch(args);
    }
}
