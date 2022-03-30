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
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;
import utility.AppointmentQuery;
import utility.CustomerQuery;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Month;
import java.util.ResourceBundle;

/** This class launches the CustomerReports screen and contains functions for user interaction with the screen. */
public class CustomerReportsController implements Initializable {

    /** This comboBox lists the various appointment types in the database. */
    public ComboBox<String> appointmentTypeComboBox;

    /** This comboBox lists each month for the appointment type report. */
    public ComboBox<Month> selectedMonthComboBox;

    /** This label is where the count of appointments of a chosen type in a chosen month is output. */
    public Label totalAppointmentCount; // where the count of appointments is output

    /** This comboBox lists each month for the percentage of customers meeting a month report. */
    public ComboBox<Month> percentageMonthComboBox;

    /** This label is where the percentage of customers of a chosen month is output. */
    public Label percentageOfCustomers;

    /** This method initializes all comboBoxes on the screen, including the two month comboBoxes and the appointment type
     * comboBox. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Appointment> allAppointments = null;
        try {
            allAppointments = AppointmentQuery.getAllAppointments();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for(Month month : Month.values()) {
            selectedMonthComboBox.getItems().add(month);
            percentageMonthComboBox.getItems().add(month);
        }

        ObservableList<String> allTypes = FXCollections.observableArrayList();

        for (Appointment appointment : allAppointments) {
            String currentType = appointment.getType();
            if ( !(allTypes.contains(currentType)) ) {
                allTypes.add(currentType);
            }
        }

        for(String type : allTypes) {
            appointmentTypeComboBox.getItems().add(type);
        }
    }

    /** When an appointment type is selected, this method checks if the month has been selected for the report yet. If
     * it has then it calculates and prints out the total of appointments with that type in that month. If not it empties
     * the totalAppointmentCount field. */
    public void onActionAppointmentTypeSelected(ActionEvent actionEvent) throws SQLException {
        ObservableList<Appointment> allAppointments = AppointmentQuery.getAllAppointments();
        int i = 0; // counter

        if (appointmentTypeComboBox.getSelectionModel().isEmpty() || selectedMonthComboBox.getSelectionModel().isEmpty()) {
            totalAppointmentCount.setText(""); //blank because we don't have information to calculate the total yet
            System.out.println("Not enough info yet");
        }
        else {
            Month selectedMonth = selectedMonthComboBox.getValue();
            String appointmentType = appointmentTypeComboBox.getValue();
            for (Appointment appointment : allAppointments) {
                if (appointment.getStart().getMonth() == selectedMonth && appointment.getType().equals(appointmentType)) {
                    i++;
                }
            }
            totalAppointmentCount.setText(Integer.toString(i));
        }
    }

    /** When a month is selected, this method checks if an appointment type has been selected. If it hasn't it sets the
     * totalAppointmentCount field to be empty. If it has then it calculates the total number of appointments of the
     * selected type in the selected month. */
    public void onActionMonthSelected(ActionEvent actionEvent) throws SQLException {
        ObservableList<Appointment> allAppointments = AppointmentQuery.getAllAppointments();
        int i = 0; // counter

        if (appointmentTypeComboBox.getSelectionModel().isEmpty() || selectedMonthComboBox.getSelectionModel().isEmpty()) {
            totalAppointmentCount.setText(""); //blank because we don't have information to calculate the total yet
            System.out.println("Not enough info yet");
        }
        else {
            Month selectedMonth = selectedMonthComboBox.getValue();
            String appointmentType = appointmentTypeComboBox.getValue();
            for (Appointment appointment : allAppointments) {
                if (appointment.getStart().getMonth() == selectedMonth && appointment.getType().equals(appointmentType)) {
                    i++;
                }
            }
            totalAppointmentCount.setText(Integer.toString(i));
        }
    }

    /** When a month is selected from the percentageMonthComboBox this method calculates what percentage of all customers
     * in the database have at least one scheduled appointment in the selected month. It then prints that percentage out
     * to the percentageOfCustomers label. */
    public void onActionPercentageMonthChosen(ActionEvent actionEvent) throws SQLException {
        ObservableList<Appointment> allAppointments = AppointmentQuery.getAllAppointments();
        ObservableList<Customer> allCustomers = CustomerQuery.getAllCustomers();
        double totalCustomers = (double) allCustomers.size();
        int i = 0; // counter

        Month selectedMonth = percentageMonthComboBox.getValue();
        for (Appointment appointment : allAppointments) {
            for (Customer customer : allCustomers) {
                if (appointment.getStart().getMonth() == selectedMonth
                        && appointment.getCustomerId() == customer.getCustomerId()) {
                    i++;
                    allCustomers.remove(customer);
                    break;
                }
            }
        }

        double percentage = ((double) i / totalCustomers) * 100;
        percentageOfCustomers.setText(String.format("%.2f", percentage) + "%");
    }

    /** This function returns the user to the MainMenu screen. */
    public void onActionReturnToMainMenu(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 400, 500);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }
}
