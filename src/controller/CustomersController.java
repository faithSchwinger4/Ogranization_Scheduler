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

public class CustomersController implements Initializable {

    private static User currentUser;
    @FXML
    private TableView<Customer> customersTable;
    @FXML
    private TableColumn<Customer, Integer> customerIdColumn;
    @FXML
    private TableColumn<Customer, String> nameColumn;
    @FXML
    private TableColumn<Customer, String> addressColumn;
    @FXML
    private TableColumn<Customer, String> countryColumn;
    @FXML
    private TableColumn<Customer, String> divisionColumn;
    @FXML
    private TableColumn<Customer, String> postalCodeColumn;
    @FXML
    private TableColumn<Customer, String> phoneNumberColumn;

    public static User getCurrentUser() {
        return currentUser;}
    public static void setCurrentUser(User currentUser) {
        CustomersController.currentUser = currentUser;}

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


    public void onActionReturnToMainMenuButtonPressed(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 400, 500);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }

    public void onActionAddCustomerButtonPressed(ActionEvent actionEvent) throws IOException {
        AddCustomerController.setCurrentUser(currentUser);

        Parent root = FXMLLoader.load(getClass().getResource("/view/AddCustomer.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 400, 550);
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.show();
    }

    public void onActionUpdateCustomerButtonPressed(ActionEvent actionEvent) throws IOException {
        UpdateCustomerController.setCurrentUser(currentUser);
        UpdateCustomerController.setCustomerToUpdate(customersTable.getSelectionModel().getSelectedItem());

        Parent root = FXMLLoader.load(getClass().getResource("/view/UpdateCustomer.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 400, 550);
        stage.setTitle("Update Customer");
        stage.setScene(scene);
        stage.show();
    }

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
    }
}
