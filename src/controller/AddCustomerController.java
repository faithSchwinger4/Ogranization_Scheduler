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
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable {

    private static User currentUser;
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
        AddCustomerController.currentUser = currentUser;
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
    }

    public void onActionCancelButtonPressed(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 500);
        stage.setTitle("Customers");
        stage.setScene(scene);
        stage.show();
    }

    public void onActionSaveButtonPressed(ActionEvent actionEvent) throws IOException, SQLException {
        // get the information needed from fields and user information
        String customerName = customerNameField.getText();
        String address = customerAddressField.getText();
        String postalCode = customerPostalCodeField.getText();
        String phoneNumber = customerPhoneNumberField.getText();
        LocalDateTime createDate = LocalDateTime.now();
        String createdBy = currentUser.getUserName();
        int divisionId = customerDivisionComboBox.getValue().getDivisionId();

        // send new customer information to the database
        CustomerQuery.insert(customerName, address, postalCode, phoneNumber, Timestamp.valueOf(createDate), createdBy,
                Timestamp.valueOf(createDate), createdBy, divisionId);

        // load the customer view screen
        Parent root = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 500);
        stage.setTitle("Customers");
        stage.setScene(scene);
        stage.show();
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
