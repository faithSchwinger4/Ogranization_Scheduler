package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.FirstLevelDivision;
import model.User;
import utility.CountryQuery;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable {

    private static User currentUser;
    public TextField customerIdField;
    public TextField customerNameField;
    public TextField customerAddressField;
    public ComboBox<Country> customerCountryComboBox;
    public ComboBox<FirstLevelDivision> customerDivisionField;
    public TextField customerPostalCodeField;
    public TextField customerPhoneNumberField;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        AddCustomerController.currentUser = currentUser;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Attempted to initialize");
        ObservableList<Country> allCountries = FXCollections.observableArrayList();
        try {
            allCountries = CountryQuery.getAllCountries();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to get the countries");
        }

        for (Country country : allCountries) {
            customerCountryComboBox.getItems().add(country);
            System.out.println("for each country");
        }
    }

    public void onActionCancelButtonPressed(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 500);
        stage.setTitle("Customers");
        stage.setScene(scene);
        stage.show();
    }

    public void onActionSaveButtonPressed(ActionEvent actionEvent) throws IOException {
        // code to create new customer, save data

        Parent root = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 500);
        stage.setTitle("Customers");
        stage.setScene(scene);
        stage.show();
    }
}
