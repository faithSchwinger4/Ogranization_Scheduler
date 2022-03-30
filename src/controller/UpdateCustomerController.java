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
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/** This class launches the UpdateCustomer screen and contains functions for user interaction with the screen. */
public class UpdateCustomerController implements Initializable {

    /** Stores the current user's information as a User object. This allows the Appointment screen to pass through the
     * current user's information. */
    private static User currentUser;

    /** This stores the customer object the user chose to update in the Customers screen. It is used to initialize
     * all the fields on this screen. */
    private static Customer customerToUpdate;


    /** The field for the customer ID to be displayed that doesn't allow the user to edit it. */
    public TextField customerIdField;

    /** The field for the customer's name to be updated. */
    public TextField customerNameField;

    /** The field for the customer's address to be updated. */
    public TextField customerAddressField;

    /** The combo box for the customer's country to be updated. */
    public ComboBox<Country> customerCountryComboBox;

    /** The combo box for the customer's first-level-division to be updated. */
    public ComboBox<FirstLevelDivision> customerDivisionComboBox;

    /** The field for the customer's postal code to be updated. */
    public TextField customerPostalCodeField;

    /** The field for the customer's phone number to be updated. */
    public TextField customerPhoneNumberField;


    /** This function sets the current user information. It allows the previous screen to pass through the information
     * for the current user.
     * @param currentUser is the User object representing the current user logged into the application */
    public static void setCurrentUser(User currentUser) {
        UpdateCustomerController.currentUser = currentUser;
    }


    /** This method initializes the country comboBox with all the possible country options.
     * <p>
     *     It also updates each text-field and comboBox with information from the Customer object the user selected from
     *     the Customers screen to update.
     * </p> */
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
            customerCountry = CountryQuery.getCountryFromID(customerToUpdate.getCountryId());
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

    /** This method returns the User to the Customers scene without updating any customer information in the database
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
     * uses it to update an existing customer in the database with a query utilizing the customer's ID. It then returns
     * the user to the Customers screen of the application. */
    public void onActionSaveButtonPressed(ActionEvent actionEvent) throws IOException, SQLException {
        int customerId = customerToUpdate.getCustomerId();
        String customerName = customerNameField.getText();
        String address = customerAddressField.getText();
        String postalCode = customerPostalCodeField.getText();
        String phoneNumber = customerPhoneNumberField.getText();
        LocalDateTime lastUpdate = LocalDateTime.now();
        String lastUpdatedBy = currentUser.getUserName();
        int divisionId = customerDivisionComboBox.getValue().getDivisionId();

        CustomerQuery.update(customerId, customerName, address, postalCode, phoneNumber, Timestamp.valueOf(lastUpdate),
                lastUpdatedBy, divisionId);

        Parent root = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 500);
        stage.setTitle("Customers");
        stage.setScene(scene);
        stage.show();
    }

    /** This function sets the information for the customer the user wants to update in the form of a Customer object.
     * This allows it to be passed from the Customers screen to the UpdateCustomers screen.
     * @param customerToUpdate is the Customer object storing the original customer information */
    public static void setCustomerToUpdate(Customer customerToUpdate) {
        UpdateCustomerController.customerToUpdate = customerToUpdate;
    }

    /** When a country is selected from the countryComboBox, this method then sets the divisionComboBox options to the
     * corresponding divisions that are in the selected country. It does this by clearing the old divisions from the
     * comboBox and then adding in the ones that correspond to the selected country. */
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
