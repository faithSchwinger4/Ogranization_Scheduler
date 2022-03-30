package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;
import model.User;
import utility.AppointmentQuery;
import utility.CustomerQuery;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/** This class launches the Customers screen and contains functions for user interaction with the screen. */
public class CustomersController implements Initializable {

    /** Stores the current user's information as a User object. This allows the Appointment screen to pass through the
     * current user's information. */
    private static User currentUser;

    /** This label is where the deleted appointment information and confirmation are printed to. It is also where error
     * statements are printed to. */
    public Label deletedCustomerConfirmation;

    /** This table is where the customers are all displayed. */
    @FXML
    private TableView<Customer> customersTable;

    /** This column is where the customerIds are listed for each Customer. */
    @FXML
    private TableColumn<Customer, Integer> customerIdColumn;

    /** This column is where the names are listed for each Customer. */
    @FXML
    private TableColumn<Customer, String> nameColumn;

    /** This column is where the addresses are listed for each Customer. */
    @FXML
    private TableColumn<Customer, String> addressColumn;

    /** This column is where the countries are listed for each Customer. */
    @FXML
    private TableColumn<Customer, String> countryColumn;

    /** This column is where the first-level-divisions are listed for each Customer. */
    @FXML
    private TableColumn<Customer, String> divisionColumn;

    /** This column is where the postal codes are listed for each Customer. */
    @FXML
    private TableColumn<Customer, String> postalCodeColumn;

    /** This column is where the phone numbers are listed for each Customer. */
    @FXML
    private TableColumn<Customer, String> phoneNumberColumn;


    /** This function sets the current user information. It allows the previous screen to pass through the information
     * for the current user.
     * @param currentUser is the User object representing the current user logged into the application */
    public static void setCurrentUser(User currentUser) {
        CustomersController.currentUser = currentUser;}

    /** This method initializes the screen by filling the table with all the Customers from the database and sets the
     * cellValueFactories for each column. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

        try {
            allCustomers = CustomerQuery.getAllCustomers();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        customersTable.setItems(allCustomers);

        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("countryName"));
        divisionColumn.setCellValueFactory(new PropertyValueFactory<>("divisionName"));
        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
    }

    /** This method returns the user to the MainMenu screen without affecting any of the appointments. */
    public void onActionReturnToMainMenuButtonPressed(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 400, 500);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }

    /** This method launches the AddCustomer screen when the "Add" button is pressed. */
    public void onActionAddCustomerButtonPressed(ActionEvent actionEvent) throws IOException {
        AddCustomerController.setCurrentUser(currentUser);

        Parent root = FXMLLoader.load(getClass().getResource("/view/AddCustomer.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 400, 550);
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.show();
    }

    /** This method launches the UpdateCustomer screen when the "Update" button is pressed. It passes a Customer object
     * to the screen by setting a static User member so that the customer's information can be initialized to the
     * various fields on the next screen. */
    public void onActionUpdateCustomerButtonPressed(ActionEvent actionEvent) throws IOException {
        try {
            UpdateCustomerController.setCurrentUser(currentUser);
            UpdateCustomerController.setCustomerToUpdate(customersTable.getSelectionModel().getSelectedItem());

            Parent root = FXMLLoader.load(getClass().getResource("/view/UpdateCustomer.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 400, 550);
            stage.setTitle("Update Customer");
            stage.setScene(scene);
            stage.show();
        }
        catch(Exception e) {
            deletedCustomerConfirmation.setText("ERROR: Please select a customer to update.");
        }
    }

    /** This method deletes a Customer that was selected from the table from the database. It then displays the
     * confirmation of and the specifics for the Customer that was deleted.It then updates the customer table
     * to display only the current customers in the database. */
    public void onActionDeleteCustomer(ActionEvent actionEvent) throws SQLException {
        int customerIdToDelete = customersTable.getSelectionModel().getSelectedItem().getCustomerId();

        // find the customer's appointments, place in an OL
        ObservableList<Appointment> customerAppointments = FXCollections.observableArrayList();
        customerAppointments = AppointmentQuery.getCustomerAppointments(customerIdToDelete);

        // delete using for loop and appt query
        for (Appointment appointment : customerAppointments) {
            AppointmentQuery.delete(appointment.getAppointmentId());
            System.out.println("deleting appointments");
        }

        // delete the customer from db
        CustomerQuery.delete(customerIdToDelete);

        ObservableList<Customer> currentCustomers = CustomerQuery.getAllCustomers();
        customersTable.setItems(currentCustomers);

        deletedCustomerConfirmation.setText("Customer " + customerIdToDelete + " was deleted.");
    }
}
