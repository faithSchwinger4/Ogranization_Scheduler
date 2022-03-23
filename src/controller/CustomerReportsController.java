package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import model.Appointment;
import utility.AppointmentQuery;

import java.net.URL;
import java.sql.SQLException;
import java.time.Month;
import java.util.ResourceBundle;

public class CustomerReportsController implements Initializable {

    public ComboBox<String> appointmentTypeComboBox;
    public ComboBox<Month> selectedMonthComboBox;
    public Label totalAppointmentCount; // where the count of appointments is output

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
}