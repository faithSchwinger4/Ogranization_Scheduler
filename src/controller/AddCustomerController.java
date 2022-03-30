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
import model.FirstLevelDivision;
import model.User;
import utility.CountryQuery;
import utility.CustomerQuery;
import utility.FirstLevelDivisionQuery;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/** This class launches the AddCustomer screen and contains functions for user interaction with the screen. */
public class AddCustomerController implements Initializable {

    /** Stores the current user's information as a User object. This allows the Appointment screen to pass through the
     * current user's information. */
    private static User currentUser;

    /** The field for the new customer's name to be input. */
    public TextField customerNameField;

    /** The field for the new customer's address to be input. */
    public TextField customerAddressField;

    /** The combo box for the customer's country to be chosen. */
    public ComboBox<Country> customerCountryComboBox;

    /** The combo box for the customer's first-level-division to be chosen. */
    public ComboBox<FirstLevelDivision> customerDivisionComboBox;

    /** The field for the new customer's postal code to be input. */
    public TextField customerPostalCodeField;

    /** The field for the new customer's phone number to be input. */
    public TextField customerPhoneNumberField;

    /** This function sets the current user information. It allows the previous screen to pass through the information
     * for the current user.
     * @param currentUser is the User object representing the current user logged into the application */
    public static void setCurrentUser(User currentUser) {
        AddCustomerController.currentUser = currentUser;
    }

    /** This method initializes the country comboBox with all the possible country options. */
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

    /** This method returns the User to the Customers scene without adding any new customer information to the database
     * when the cancel button is pressed. */
    public void onActionCancelButtonPressed(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 500);
        stage.setTitle("Customers");
        stage.setScene(scene);
        stage.show();
    }

    /** When the save button is clicked, this method pulls all the information from the text-fields and comboBoxes and
     * uses it to insert a new customer into the database with a query. It then returns the user to the Customers screen
     * of the application. */
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

    /** When a country is selected from the countryComboBox, this method then sets the divisionComboBox options to the
     * corresponding divisions that are in the selected country. It does this by clearing the old divisions from the
     * comboBox and then adding in the ones that correspond to the selected country. */
    public void onActionCountrySelected(ActionEvent actionEvent) throws SQLException {
        customerDivisionComboBox.setValue(null);
        customerDivisionComboBox.getItems().clear(); // empty the current division list so the new one can replace it

        Country selectedCountry = customerCountryComboBox.getValue();
        int countryId = selectedCountry.getCountryId();

        ObservableList<FirstLevelDivision> divisionsForSelectedCountry = FXCollections.observableArrayList();
        divisionsForSelectedCountry = FirstLevelDivisionQuery.getDivisionsInOneCountry(countryId);

        for (FirstLevelDivision division : divisionsForSelectedCountry) {
            customerDivisionComboBox.getItems().add(division);
        }
    }
}
