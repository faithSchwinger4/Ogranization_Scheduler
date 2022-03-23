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
import utility.CustomerQuery;
import utility.FirstLevelDivisionQuery;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UpdateCustomerController implements Initializable {

    private static User currentUser;
    private static Customer customerToUpdate;

    public TextField customerIdField;
    public TextField customerNameField;
    public TextField customerAddressField;
    public ComboBox<Country> customerCountryComboBox;
    public ComboBox<FirstLevelDivision> customerDivisionComboBox;
    public TextField customerPostalCodeField;
    public TextField customerPhoneNumberField;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        UpdateCustomerController.currentUser = currentUser;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Country> allCountries = FXCollections.observableArrayList();
        try {
            allCountries = CountryQuery.getAllCountries();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Country country : allCountries) {
            customerCountryComboBox.getItems().add(country);
        }

        customerIdField.setText(Integer.toString(customerToUpdate.getCustomerId()));
        customerNameField.setText(customerToUpdate.getCustomerName());
        customerAddressField.setText(customerToUpdate.getAddress());
        customerPostalCodeField.setText(customerToUpdate.getPostalCode());
        customerPhoneNumberField.setText(customerToUpdate.getPhoneNumber());

        Country customerCountry = null; // get country object to initialize combobox
        try {
            customerCountry = CountryQuery.getCountryFromID(customerToUpdate.getCustomerId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        customerCountryComboBox.setValue(customerCountry); //initialize combobox

        // fill the division list for the customer's current country
        ObservableList<FirstLevelDivision> divisionsForSelectedCountry = FXCollections.observableArrayList();
        try {
            divisionsForSelectedCountry = FirstLevelDivisionQuery.getDivisionsInOneCountry(customerToUpdate.getCountryId());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (FirstLevelDivision division : divisionsForSelectedCountry) {
            customerDivisionComboBox.getItems().add(division);
        }

        // get the current division object to initialize the combobox
        FirstLevelDivision customerDivision = null; //need query retrieve single division
        try {
            customerDivision = FirstLevelDivisionQuery.getDivisionFromId(customerToUpdate.getDivisionId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        customerDivisionComboBox.setValue(customerDivision);
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
        Parent root = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 500);
        stage.setTitle("Customers");
        stage.setScene(scene);
        stage.show();
    }

    public static Customer getCustomerToUpdate() {
        return customerToUpdate;
    }

    public static void setCustomerToUpdate(Customer customerToUpdate) {
        UpdateCustomerController.customerToUpdate = customerToUpdate;
    }

    public void onActionCountrySelected(ActionEvent actionEvent) throws SQLException {
        customerDivisionComboBox.setValue(null);
        customerDivisionComboBox.getItems().clear();

        Country selectedCountry = customerCountryComboBox.getValue();
        int countryId = selectedCountry.getCountryId();

        ObservableList<FirstLevelDivision> divisionsForSelectedCountry = FXCollections.observableArrayList();
        divisionsForSelectedCountry = FirstLevelDivisionQuery.getDivisionsInOneCountry(countryId);

        for (FirstLevelDivision division : divisionsForSelectedCountry) {
            customerDivisionComboBox.getItems().add(division);
        }
    }
}
